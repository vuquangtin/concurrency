package concurrency.java.blocking;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Memoizer<A, V> implements Computable<A, V> {
	private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, ? extends V> c;

	public Memoizer(Computable<A, ? extends V> c) {
		this.c = c;
	}

	@Override
	public V compute(A arg) throws InterruptedException {
		Future<V> vFuture = cache.get(arg);
		if (vFuture == null) {
			Callable<V> callable = () -> c.compute(arg);
			FutureTask<V> futureTask = new FutureTask<V>(callable);
			vFuture = cache.putIfAbsent(arg, futureTask);
			if (vFuture == null) {
				vFuture = futureTask;
				futureTask.run();
			}
		}
		try {
			return vFuture.get();
		} catch (ExecutionException e) {
			e.printStackTrace();
			throw launderThrowable(e.getCause());
		}
	}

	public static RuntimeException launderThrowable(Throwable t) {
		if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		} else if (t instanceof Error) {
			throw (Error) t;
		} else {
			throw new IllegalStateException("Not unchecked", t);
		}
	}
}