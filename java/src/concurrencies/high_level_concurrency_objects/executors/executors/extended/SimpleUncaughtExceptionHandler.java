package executors.extended;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SimpleUncaughtExceptionHandler implements UncaughtExceptionHandler {

	protected Thread thread;
	protected Throwable throwable;

	public void uncaughtException(Thread t, Throwable e) {

		this.thread = t;
		this.throwable = e;
	}

	public Thread getThread() {

		return thread;
	}

	public Throwable getThrowable() {

		return throwable;
	}
}