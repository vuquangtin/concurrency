package com.deadlocks;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class DeadlockExample {
	private static Object Lock1 = new Object();
	private static Object Lock2 = new Object();

	public static void main(String args[]) {

		ThreadDemo1 T1 = new ThreadDemo1();
		ThreadDemo2 T2 = new ThreadDemo2();
		T1.start();
		T2.start();
	}

	private static class ThreadDemo1 extends Thread {
		public void run() {
			synchronized (Lock1) {
				System.out.println("Thread 1: Holding lock1 1");

				// With forced "Thread.sleep(10)" we are trying to simulate that
				// thread is working on something for 10 milliseconds.
				// This let us to demonstrate the deadlock scenario better.
				try {
					Thread.sleep(10);
				} catch (InterruptedException ignored) {
				}

				System.out.println("Thread 1: Waiting for lock1 2");
				synchronized (Lock2) {
					System.out.println("Thread 1: Holding lock1 1 & 2");
				}
			}
		}
	}

	private static class ThreadDemo2 extends Thread {
		public void run() {
			synchronized (Lock2) {
				System.out.println("Thread 2: Holding lock1 2");

				try {
					Thread.sleep(10);
				} catch (InterruptedException ignored) {
				}

				System.out.println("Thread 2: Waiting for lock1 1");
				synchronized (Lock1) {
					System.out.println("Thread 2: Holding lock1 1 & 2");
				}
			}
		}
	}
}
