package kills;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/***
 * 2. Interrupting a Thread
 * 
 * Above code looks good but have main points which need to be taken into
 * account before we take above approach
 * 
 * It will become more complicated in case we have lots of threads and we need
 * to ensure that those are finished by using join() method. Do we really need
 * to define a Boolean flag as Java already provide this feature using
 * interrupted flag? There are a couple of important things in this code block
 * 
 * We are not swallowing the exception but passing it on to the system to ensure
 * corrective action is being taken by system based on this exception
 * 
 * In the above example, we caught InterruptedException and immediately used
 * Thread.currentThread().interrupt() to interrupt our thread immediately.This
 * is required since the interrupted flag is cleared once the exception is
 * thrown and it can cause issue in case our code is running in the nested loop.
 * 
 * Just to summarize, Thread.interrupt() is the preferred/recommended way to
 * cleanly Kill Java Thread if you still want to use flag approach you may have
 * to wait for all the blocking operations to finish before you can use your
 * flag based logic.
 * 
 * @author vuquangtin
 *
 */
public class InterruptedExceptionExample implements Runnable {

	private final AtomicBoolean running = new AtomicBoolean(true);

	/**
	 * When an object implementing interface <code>Runnable</code> is used to
	 * create a thread, starting the thread causes the object's <code>run</code>
	 * method to be called in that separately executing thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may take
	 * any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run() {
		try {

			while (running.get()) {
				for (int i = 0; i < 3; i++) {
					System.out.println(i);
				}

				Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<?> future = executor.submit(new InterruptedExceptionExample());
		Thread.sleep(3000);
		executor.shutdownNow();
	}
}