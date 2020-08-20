package concurrency.java.wrappers;

import java.util.Set;
import java.util.concurrent.Callable;

import com.google.common.collect.ImmutableSet;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CallableWrappers {

	private CallableWrappers() {
	}

	public static CallableWrapper combine(Iterable<CallableWrapper> wrappers) {
		return new CombinedCallableWrapper(wrappers);
	}

	private static class CombinedCallableWrapper extends CallableWrapper {
		private final Set<CallableWrapper> wrappers;

		public CombinedCallableWrapper(Iterable<CallableWrapper> wrappers) {
			this.wrappers = ImmutableSet.copyOf(wrappers);
		}

		@Override
		public <T> Callable<T> wrap(Callable<T> callable) {
			for (CallableWrapper w : wrappers) {
				callable = w.wrap(callable);
			}
			return callable;
		}

		@Override
		public Runnable wrap(Runnable runnable) {
			for (CallableWrapper w : wrappers) {
				runnable = w.wrap(runnable);
			}
			return runnable;
		}
	}
}