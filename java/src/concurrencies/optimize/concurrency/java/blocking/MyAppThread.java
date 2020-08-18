package concurrency.java.blocking;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MyAppThread extends Thread {
	static Logger logger = Logger.getLogger(MyAppThread.class.getName());
	public static final String DEFAULT_NAME = "MyAppThread";
	private static volatile boolean debugLifecycle = false;
	private static final AtomicInteger created = new AtomicInteger();
	private static final AtomicInteger alive = new AtomicInteger();

	private Map map = new ConcurrentHashMap();

	public MyAppThread(Runnable r) {
		this(r, DEFAULT_NAME);
	}

	public MyAppThread(Runnable runnable, String name) {
		super(runnable, name + "-" + created.incrementAndGet());
		setUncaughtExceptionHandler((t, e) -> {
			logger.debug("UNCAUGHT in thread " + t.getName(), e);
		});
	}

	@Override
	public void run() {
		// 复制debug，确保他的值自始至终是一致的
		boolean debug = debugLifecycle;
		if (debug) {
			logger.debug("Created " + getName());
		}
		try {
			alive.incrementAndGet();
			super.run();
		} finally {
			alive.decrementAndGet();
			if (debug) {
				logger.debug("Exiting" + getName());
			}
		}
	}

	public static int getThreadsCreated() {
		return created.get();
	}

	public static int getThreadsAlive() {
		return alive.get();
	}

	public static boolean getDebug() {
		return debugLifecycle;
	}

	public static void setDebug(boolean b) {
		debugLifecycle = b;
	}
}