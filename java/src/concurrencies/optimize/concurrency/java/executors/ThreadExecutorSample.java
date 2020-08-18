package concurrency.java.executors;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/

 *      vuquangtin/concurrency</a>
 *
 */
public class ThreadExecutorSample {

	public static void main(String args[]) {

		ExecutorService executor = Executors.newFixedThreadPool(10);
		// executor.submit(()->System.out.println("Hi"));
		// executor.submit(()->System.out.println("Bye"));
		// System.out.println(executor.isShutdown());

		BoundedExecutor bexec = new BoundedExecutor(executor, 10);
		try {
			for (int i = 0; i <= 100; i++) {
				bexec.submitTask(new Runnable() {

					@Override
					public void run() {
						System.out.println("Hi");
						try {
							Thread.sleep(new Random().nextInt(500));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		} catch (InterruptedException ie) {
			executor.shutdown();
		} finally {
			executor.shutdown();
		}

	}

}

class BoundedExecutor {
	Executor executor;
	Semaphore semaphore;

	public BoundedExecutor(Executor exec, int bound) {
		semaphore = new Semaphore(bound);
		executor = exec;
	}

	public void submitTask(final Runnable command) throws InterruptedException {

		semaphore.acquire();
		try {
			executor.execute(new Runnable() {
				public void run() {
					try {
						command.run();
					} finally {
						semaphore.release();
					}
				}
			});
		} catch (RejectedExecutionException rje) {
			semaphore.release();
		}

	}
}