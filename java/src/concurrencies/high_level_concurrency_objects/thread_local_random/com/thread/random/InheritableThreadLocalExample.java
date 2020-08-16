package com.thread.random;

import java.util.UUID;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class InheritableThreadLocalExample {
	public static void main(String[] args) {
		new Thread(new Worker("worker1")).start();
		// new Thread(new Worker("worker2")).start();
	}
}

class TransactionManager {
	private static final InheritableThreadLocal<String> context = new InheritableThreadLocal<String>() {
		protected String childValue(String arg0) {
			System.out.println("childValue has been called.");
			return arg0;
		}
	};

	public static void startTransaction() {
		context.set(UUID.randomUUID().toString());
	}

	public static String getTransactionId() {
		return context.get();
	}

	public static void endTransaction() {
		context.remove();
	}

	public static void setChild(String x) {
		context.set(x);
	}
}

class Worker implements Runnable {
	private final String name;

	Worker(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		TransactionManager.startTransaction();
		System.out.println(name + " after start - " + TransactionManager.getTransactionId());
		SubWorker subWorker = new SubWorker("sub" + name);
		System.out.println("Creating object of sub worker thread");
		Thread subWorkerThread = new Thread(subWorker);
		System.out.println("Sub thread is going to start");
		subWorkerThread.start();
		System.out.println("Sub thread already started.");
		try {
			subWorkerThread.join();
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		System.out.println(name + " after start - " + TransactionManager.getTransactionId());
		TransactionManager.endTransaction();
		System.out.println(name + " after end - " + TransactionManager.getTransactionId());
	}
}

class SubWorker implements Runnable {
	private final String name;

	SubWorker(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		TransactionManager.setChild("dfdfssd");
		System.out.println("Sub thread started execution run method.");
		System.out.println(name + " - " + TransactionManager.getTransactionId());
	}
}