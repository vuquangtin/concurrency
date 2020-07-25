package com.patterns.activeobject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyActiveObject {

	private double val = 0.0;

	private BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<Runnable>();

	public MyActiveObject() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						dispatchQueue.take().run();
					} catch (InterruptedException e) {
						// okay, just terminate the dispatcher
					}
				}
			}
		}).start();
	}

	void doSomething() throws InterruptedException {
		dispatchQueue.put(new Runnable() {
			@Override
			public void run() {
				val = 1.0;
			}
		});
	}

	void doSomethingElse() throws InterruptedException {
		dispatchQueue.put(new Runnable() {
			@Override
			public void run() {
				val = 2.0;
			}
		});
	}
}
