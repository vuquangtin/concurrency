package concurrency.java.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class ExecutorServiceAPI {

	public static void main(String[] args) throws InterruptedException {
		// shutdownMethod();
		// terminate();
		// executeRunnableError();
		executeRunnableTask();
	}

	private static void shutdownMethod() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		IntStream.range(0, 10).boxed().forEach(i -> {
			executorService.execute(() -> {
				System.out.println(i);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// e.printStackTrace();
				}

			});
		});
		System.out.println(executorService.isShutdown());
		executorService.shutdown();
		// executorService.shutdownNow();
		System.out.println(executorService.isShutdown());
		executorService.execute(() -> System.out.println("when shutdown, can execute new task???"));

	}

	public static void terminate() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		IntStream.range(0, 10).boxed().forEach(i -> {
			executorService.execute(() -> {
				System.out.println(i);
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		});

		executorService.shutdown();
		System.out.println(executorService.isShutdown());
		System.out.println(executorService.isTerminated());
		System.out.println(((ThreadPoolExecutor) executorService).isTerminating());
	}

	public static void executeRunnableError() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10, new MyThreadFactory());

		IntStream.range(0, 20).forEach(i -> executorService.execute(() -> System.out.println(1 / 0)));
		executorService.shutdown();
		executorService.awaitTermination(10, TimeUnit.SECONDS);
		System.out.println("-----------------------------------------");
	}

	public static void executeRunnableTask() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		IntStream.range(0, 20).boxed().forEach(i -> {
			executorService.execute(new MyRunnable(i) {
				@Override
				protected void done() {
					System.out.println("the task num" + this.getNum() + "is done");
				}

				@Override
				protected void doInit() {
					System.out.println("the task num" + this.getNum() + "initing....");
				}

				@Override
				protected void doExecute() {
					if (this.getNum() % 3 == 0) {
						int a = 1 / 0;
					}
				}

				@Override
				protected void errorProcess() {
					System.out.println("the task num" + this.getNum() + "is processing error");
				}
			});
		});

		executorService.shutdown();
	}

	/**
	 * define the thread factory to catch the task cause
	 */
	public static class MyThreadFactory implements ThreadFactory {

		private final AtomicInteger SEQ = new AtomicInteger();

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setName("my thread-" + SEQ.getAndIncrement());
			thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					System.out.println(t.getName() + "happen error or execption,");
					e.printStackTrace();
					System.out.println("------------------------------------");
				}
			});
			return thread;
		}
	}

	private static abstract class MyRunnable implements Runnable {
		private final int num;

		public MyRunnable(int num) {
			this.num = num;
		}

		public int getNum() {
			return num;
		}

		@Override
		public void run() {
			try {
				this.doInit();
				this.doExecute();
				this.done();
			} catch (Throwable throwable) {
				this.errorProcess();
			}
		}

		protected abstract void done();

		protected abstract void doInit();

		protected abstract void doExecute();

		protected abstract void errorProcess();

	}
}
