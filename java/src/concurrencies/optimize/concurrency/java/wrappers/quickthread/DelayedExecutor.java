package concurrency.java.wrappers.quickthread;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class DelayedExecutor {

	private static interface Callback {
		void onComplete(CallableWithId<?, ?> callable);
	}

	private static Callback NOOP = new Callback() {
		@Override
		public void onComplete(CallableWithId<?, ?> callable) {

		}

	};

	private static class CallableWrapper<V> implements Callable<V> {

		CallableWithId<V, ?> callable = null;
		Object id = null;

		Callback callback = NOOP;

		public CallableWrapper(CallableWithId<V, ?> callable) {
			this.callable = callable;
			this.id = callable.getId();
		}

		public void addCallback(Callback callback) {
			this.callback = callback;
		}

		public void removeCallback() {
			this.callback = NOOP;
		}

		@Override
		public V call() throws Exception {
			V v = this.callable.call();
			this.callback.onComplete(this.callable);
			return v;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CallableWrapper<?> other = (CallableWrapper<?>) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}

		public CallableWithId<V, ?> getCallable() {
			return callable;
		}
	}

	private static final ScheduledExecutorService worker = Executors
			.newSingleThreadScheduledExecutor();

	public static final Map<Object, Future<?>> futures = new ConcurrentHashMap<Object, Future<?>>();

	private static final Map<Future<?>, Object> reversedFutures = new ConcurrentHashMap<Future<?>, Object>();

	private static class ScheduledFutureImpl<V> implements ScheduledFuture<V> {

		private ScheduledFuture<V> future;

		public ScheduledFutureImpl(ScheduledFuture<V> future) {
			super();
			this.future = future;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return future.getDelay(unit);
		}

		@Override
		public int compareTo(Delayed delayed) {
			return this.future.compareTo(delayed);
		}

		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			synchronized (futures) {
				Object callableID = reversedFutures.get(this);
				// System.out.println("---caller id =" + callableID );
				futures.remove(callableID);
				reversedFutures.remove(this);
				return future.cancel(mayInterruptIfRunning);
			}

		}

		@Override
		public V get() throws InterruptedException, ExecutionException {
			return future.get();
		}

		@Override
		public V get(long timeout, TimeUnit unit) throws InterruptedException,
				ExecutionException, TimeoutException {
			return future.get(timeout, unit);
		}

		@Override
		public boolean isCancelled() {
			return future.isCancelled();
		}

		@Override
		public boolean isDone() {
			return future.isDone();
		}

	}

	private static Callback CLEANUP = new Callback() {
		@Override
		public void onComplete(CallableWithId<?, ?> callable) {
			synchronized (futures) {
				Future<?> future = futures.get(callable.getId());
				// System.out.println("Oncomplete called----");
				futures.remove(callable.getId());
				reversedFutures.remove(future);
			}

		}
	};

	public static <V> Future<V> run(CallableWithId<V, ?> callable, long time,
			TimeUnit timeUnit) {
		CallableWrapper<V> wrapper = new CallableWrapper<V>(callable);
		wrapper.addCallback(CLEANUP);
		cancelTask(wrapper);

		ScheduledFuture<V> future = worker.schedule(wrapper, time, timeUnit);
		ScheduledFutureImpl<V> future2 = new ScheduledFutureImpl<V>(future);
		futures.put(callable.getId(), future2);
		reversedFutures.put(future2, callable.getId());
		return future2;

	}

	private static void cancelTask(CallableWrapper<?> wrapper) {
		Future<?> old = futures.get(wrapper.getCallable().getId());
		if (old != null) {
			synchronized (futures) {
				try {
					old.cancel(true);
				} catch (Exception e) {
				}
				futures.remove(wrapper.getCallable().getId());
				reversedFutures.remove(old);
			}

		}
	}

	@SuppressWarnings("unchecked")
	public static void cancel(CallableWithId<?, ?> callable) {
		CallableWrapper<?> wrapper = new CallableWrapper(callable);
		cancelTask(wrapper);

	}

	// for testing
	private static class MyCallable extends CallableWithId<Boolean, String> {

		public MyCallable(String id) {
			super(id);
		}

		@Override
		public Boolean call() throws Exception {
			System.out.println("I am done");
			return true;
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyCallable callable = new MyCallable("callable-1");
		MyCallable callable2 = new MyCallable("callable-2");

		Future<Boolean> future = DelayedExecutor.run(callable, 2,
				TimeUnit.SECONDS);
		// DelayedExecutor.cancel(callable );
		future.cancel(true);
		// System.out.println("Size in DelayedExecutor.futures==" +
		// DelayedExecutor.futures.size() );

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Size in DelayedExecutor.futures=="
				+ DelayedExecutor.futures.size());

		System.out.println("========EXITING==========");
	}

}