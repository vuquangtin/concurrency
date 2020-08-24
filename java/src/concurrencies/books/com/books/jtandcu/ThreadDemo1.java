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
public class ThreadDemo1 {
	public static void main(String[] args) {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				String name = Thread.currentThread().getName();
				int count = 0;

				while (!Thread.interrupted()) {
					System.out.println(name + ":" + count++);
				}
			}
		};
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);
		t1.start();
		t2.start();
		while (true) {
			double n = Math.random();
			if (n >= 0.49999999 && n <= 0.50000001)
				break;
		}
		t1.interrupt();
		t2.interrupt();
	}
}
