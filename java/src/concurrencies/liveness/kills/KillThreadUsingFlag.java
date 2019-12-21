package kills;

import java.util.concurrent.atomic.AtomicBoolean;

/***
 * Introduction
 * 
 * Oracle deprecated Thread.stop, Thread.suspend, Thread.resume and
 * Runtime.runFinalizersOnExit because of some underlying thread safety issues.
 * There are few recognized and well-accepted ways to accomplish this and in
 * this article, we will cover 2 approached to Kill a Thread in Java.
 * 
 * Using a Flag Interrupting a Thread.
 * 
 * 
 * 1. Use a Flag
 * 
 * One of the easy approaches is to use thread to show if a Thread is running or
 * not and use this flag to take corrective action based on your requirement,
 * here is a sample code outlining how to Kill Java Thread using a flag. In the
 * above example. we can control execution by setting our running variable to
 * false.In our example, we have used AtomicBoolean for concurrency, in case you
 * do not want to use it (which is not recommended), you need to make sure that
 * Boolean flag used by you in your code should be volatile.
 * 
 * @author vuquangtin
 *
 */
public class KillThreadUsingFlag implements Runnable {

	private final AtomicBoolean running = new AtomicBoolean(true);
	private Thread thread;

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

		while (running.get()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
			}
		}
		System.out.println("Shutting down thread");
	}

	public void shutdown() {
		running.set(false);
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	public static void main(String[] args) throws InterruptedException {
		KillThreadUsingFlag process = new KillThreadUsingFlag();
		process.start();
		Thread.sleep(5000);
		process.shutdown();
	}
}