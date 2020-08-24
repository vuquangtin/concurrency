package com.books.jtandcu;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class DeadlockDemo {
	private final Object lock1 = new Object();
	private final Object lock2 = new Object();

	public void instanceMethod1() {
		synchronized (lock1) {
			synchronized (lock2) {
				// lock1 then lock2
				System.out.println("first thread in instanceMethod1");
			}
		}
	}

	public void instanceMethod2() {
		synchronized (lock2) {
			synchronized (lock1) {
				// lock2 then lock1
				System.out.println("second thread in instanceMethod2");
			}
		}
	}

	public static void main(String[] args) {
		final DeadlockDemo dldemo = new DeadlockDemo();
		Runnable r1 = () -> {
			while (true) {
				dldemo.instanceMethod1();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {

				}
			}
		};
		Runnable r2 = () -> {
			while (true) {
				dldemo.instanceMethod2();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {

				}
			}
		};
		Thread thd1 = new Thread(r1);
		Thread thd2 = new Thread(r2);
		thd1.start();
		thd2.start();
	}
}