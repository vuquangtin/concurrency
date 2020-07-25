package threadpools.types;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example1 {

	public static void main(String[] args) {

		/*-
		 * Create a thread pool of fixed size, this will create as many threads and keep
		 * when a request comes for task execute one thread from the pool will pick the
		 * task and start
		 * 
		 * Thread pool uses blocking queue
		 */
		ExecutorService service = Executors.newFixedThreadPool(5);

		/* Submit tasks to thread pool */
		service.execute(new Task1());
		service.execute(new Task2());
		service.execute(new Task3());

		service.shutdown();
		final int x = 10;
		init(x);
		final Task1 task=new Task1();
		init(task);
	}

	public static void init(Task1 k) {
		 k=new Task1();
	}

	public static void init(int k) {
	}

	static class Task1 implements Runnable {
		@Override
		public void run() {
			System.out.println("Running task 1");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	static class Task2 implements Runnable {
		@Override
		public void run() {
			System.out.println("Running task 2");

		}
	}

	static class Task3 implements Runnable {
		@Override
		public void run() {
			System.out.println("Running task 3");

		}
	}
}