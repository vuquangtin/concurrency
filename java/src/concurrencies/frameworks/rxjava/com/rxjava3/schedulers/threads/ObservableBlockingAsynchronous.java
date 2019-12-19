package com.rxjava3.schedulers.threads;

import org.junit.Test;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ObservableBlockingAsynchronous {

	@Test
	public void testBackToMainThread() throws InterruptedException {

		System.out.println("done");
	}

	private void processExecution() {
		System.out.println("Execution in " + Thread.currentThread().getName());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}