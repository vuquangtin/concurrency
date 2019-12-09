package blockingqueues.arrays;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



/**
 * This is an abnormal use of locks for basic unlock/lock demonstration and not
 * the typical use of locking and unlocking using the reentrant lock
 */
public class ArrayBlockingQueueMain {
	public static final String EOF = "EOF";

	public static void main(String[] args) {
		// Array list isn't thread safe, it needs to be synchronized below
		ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<String>(6);
		ExecutorService executorService = Executors.newFixedThreadPool(5); // set
																			// up
																			// a
																			// thread
																			// pool
																			// and
																			// fix
																			// it
																			// to
																			// 3
		MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_GREEN);
		MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE);
		MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN);

		executorService.execute(producer); // add things to the buffer
		executorService.execute(consumer1); // remove things from the buffer
		executorService.execute(consumer2); // remove things from the buffer

		// this blocks until result is available
		Future<String> future = executorService.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println(ThreadColor.ANSI_RED + "I'm being printed for the Callable class");
				return "This is the callable result";
			}
		});

		try {
			System.out.println(future.get());
		} catch (ExecutionException e) {
			System.out.println("Something went wrong");
		} catch (InterruptedException e) {
			System.out.println("Thread running the task was interrupted");
		}

		executorService.shutdown();
	}
}

class MyProducer implements Runnable {
	private ArrayBlockingQueue<String> buffer;
	private String color;

	public MyProducer(ArrayBlockingQueue<String> buffer, String color) {
		this.buffer = buffer;
		this.color = color;
	}

	public void run() {
		String[] nums = { "1", "2", "3", "4", "5" };
		// add a number to the buffer array list
		for (String num : nums) {
			try {
				System.out.println(color + "Adding... " + num);
				buffer.put(num); // put is thread safe so it doesnt need
									// synchronization
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Producer was interrupted");
			}
		}
		System.out.println(color + "Adding EOF and exiting...");
		try {
			buffer.put("EOF");
		} catch (InterruptedException e) {

		}
	}
}

class MyConsumer implements Runnable {
	private ArrayBlockingQueue<String> buffer;
	private String color;

	public MyConsumer(ArrayBlockingQueue<String> buffer, String color) {
		this.buffer = buffer;
		this.color = color;
	}

	public void run() {
		// remove entries from the buffer
		while (true) {
			synchronized (buffer) { // synchronizing so peek doesnt hit a null
									// pointer
				// if lock is available, automatically lock
				try {
					if (buffer.isEmpty()) {
						continue;
					}
					if (buffer.peek().equals(ArrayBlockingQueueMain.EOF)) {
						System.out.println(color + "Exiting");
						break;
					} else {
						System.out.println(color + "Removed " + buffer.take());
					}
				} catch (InterruptedException e) {

				}
			}
		}
	}
}