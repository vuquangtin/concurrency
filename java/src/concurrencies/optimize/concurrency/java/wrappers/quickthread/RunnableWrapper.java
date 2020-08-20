package concurrency.java.wrappers.quickthread;


/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class RunnableWrapper implements Runnable {
	final Runnable runnable;

	public RunnableWrapper(Runnable runnable) {
		this.runnable = runnable;
	}

	public static RunnableWrapper of(Runnable r) {
		return new RunnableWrapper(r);
	}

	@Override
	public void run() {
		this.runnable.run();
	}
}