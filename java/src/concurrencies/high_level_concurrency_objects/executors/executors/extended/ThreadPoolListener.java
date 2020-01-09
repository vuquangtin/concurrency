package executors.extended;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public interface ThreadPoolListener {

	void beforeExecute(Thread t, Runnable r);

	void afterExecute(Throwable t, Runnable r);

}