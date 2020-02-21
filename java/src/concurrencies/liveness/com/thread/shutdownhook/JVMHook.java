package com.thread.shutdownhook;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class JVMHook {
	public int NUM = 20;

	public static void main(String[] args) {

		Runtime.getRuntime().addShutdownHook(new MyThread());
		Runtime.getRuntime().addShutdownHook(new MyThread());
		Runtime.getRuntime().addShutdownHook(new MyThread());
		Runtime.getRuntime().addShutdownHook(new MyThread());
		System.out.println(" Pre exit.");
		// when you have System.exit() next
		// operation won't execute
		// but shutdownHook operations will be executed
		// Runtime.getRuntime().halt(0); this will not invoke thread.it will
		// abruptly shutdown all the threads
		System.exit(0);
		System.out.println(" Post exit. ");
	}
}

class MyThread extends Thread {
	@Override
	public void run() {
		super.run();

		for (int i = 0; i < 100; i++) {
			System.out.println(" shutdown hook executed	" + i + " "
					+ Thread.currentThread().getName());
		}
	}
}