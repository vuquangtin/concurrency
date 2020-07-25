package com.patterns.activeobject;

import java.util.concurrent.ForkJoinPool;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyActiveObjectUsingJava8 {
	private double val;

	// container for tasks
	// decides which request to execute next
	// asyncMode=true means our worker thread processes its local task queue in
	// the FIFO order
	// only single thread may modify internal state
	private final ForkJoinPool fj = new ForkJoinPool(1, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);

	// implementation of active object method
	public void doSomething() throws InterruptedException {
		fj.execute(() -> {
			val = 1.0;
		});
	}

	// implementation of active object method
	public void doSomethingElse() throws InterruptedException {
		fj.execute(() -> {
			val = 2.0;
		});
	}
}