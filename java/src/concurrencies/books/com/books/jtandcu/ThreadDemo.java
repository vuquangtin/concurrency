package com.books.jtandcu;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadDemo {
	static Logger looger = Logger.getLogger(ThreadDemo.class.getName());

	public static void main(String[] args) {
		looger = Log4jUtils.initLog4j();
		boolean isDaemon =args.length != 0;
		Runnable r = new Runnable() {

			@Override
			public void run() {
				Thread t1 = Thread.currentThread();
				while (true) {
					System.out.printf("%s is %salive and in %s state%n",
							t1.getName(), t1.isAlive() ? "" : " not ",
							t1.getState());
					//looger.debug("RUN");
				}

			}
		};
		Thread thd1 = new Thread(r, "t1");
		if (isDaemon)
			thd1.setDaemon(true);
		System.out.printf("%s is %salive and in %s state%n", thd1.getName(),
				thd1.isAlive() ? "" : " not ", thd1.getState());
		Thread thd2 = new Thread(r);
		thd2.setName("t2");
		if (isDaemon)
			thd2.setDaemon(true);
		System.out.printf("%s is %salive and in %s state%n", thd2.getName(),
				thd2.isAlive() ? "" : " not ", thd2.getState());
		thd1.start();
		thd2.start();
	}
}