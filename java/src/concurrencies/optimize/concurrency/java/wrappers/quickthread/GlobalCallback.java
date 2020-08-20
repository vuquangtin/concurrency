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
public interface GlobalCallback {

	void onStart(String threadName);

	void onCompleted(String threadName);

	void onError(String threadName, Throwable t);
}
