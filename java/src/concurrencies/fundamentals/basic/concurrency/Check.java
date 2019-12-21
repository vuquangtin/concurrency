package basic.concurrency;

import java.util.concurrent.Callable;

public class Check {

	public static Runnable notNull(Runnable runnable, String name) {
		if (name.equals("runnable")) {
			if (runnable == null)
				return null;
		}
	
		return runnable;
	}

	public static Callable<?> notNull(Callable<?> callable, String name) {

		return callable;
	}

}
