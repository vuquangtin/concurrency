package basic.thread1;

import java.util.UUID;

public class ThreadLocalAndInheritableThreadLocalMain {
	public static void main(String[] args) {
		new Thread(new Worker("worker1")).start();
		new Thread(new Worker("worker2")).start();
	}
}

class TransactionManager {
	private static final ThreadLocal<String> context = new ThreadLocal<>();
	// Cho pheo lop con trong runnable su dung chung
	private static final ThreadLocal<String> contextInheritable = new InheritableThreadLocal<>();

	public static void startTransaction() {
		context.set(UUID.randomUUID().toString());
		contextInheritable.set(UUID.randomUUID().toString());
	}

	public static String getTransactionId() {
		return context.get();
	}
	public static String getTransactionInheritableId() {
		return contextInheritable.get();
	}
	public static void endTransaction() {
		contextInheritable.remove();
		context.remove();
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
		System.out.println(name + " after start - "
				+ TransactionManager.getTransactionId());
		System.out.println(name + " after start - "
				+ TransactionManager.getTransactionInheritableId());
		SubWorker subWorker = new SubWorker("sub" + name);
		Thread subWorkerThread = new Thread(subWorker);
		subWorkerThread.start();
		try {
			subWorkerThread.join();
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		TransactionManager.endTransaction();
		System.out.println(name + " after end - "
				+ TransactionManager.getTransactionId());
		System.out.println(name + " after end - "
				+ TransactionManager.getTransactionInheritableId());
	}
}

class SubWorker implements Runnable {
	private final String name;

	SubWorker(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		System.out
				.println(name + " - " + TransactionManager.getTransactionId());
		System.out
		.println(name + " - " + TransactionManager.getTransactionInheritableId());
	}
}