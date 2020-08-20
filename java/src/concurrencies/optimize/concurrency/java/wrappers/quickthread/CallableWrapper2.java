package concurrency.java.wrappers.quickthread;

import java.util.concurrent.Callable;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CallableWrapper2<V> implements Callable<V> {
	final Callable<V> callable;

	public static <V> CallableWrapper2 of(Callable<V> r) {
		return new CallableWrapper2<V>(r);
	}

	public CallableWrapper2(Callable<V> callable) {
		this.callable = callable;
	}

	@Override
	public V call() throws Exception {
		return callable.call();
	}
}