package custom.threadfactory;

import java.lang.Thread.UncaughtExceptionHandler;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/***
 * Also some very simple usage:
 * <br/>
 * ThreadFactory factory = new
 * TreadFactoryX().priority(3).stackSize(0).wrapRunnable(false).pattern(
 * "Socket workers", true). daemon(false).finishConfig();
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ThreadFactoryX implements ThreadFactory {
	// thread properties
	long stackSize;
	String pattern;
	ClassLoader ccl;
	ThreadGroup group;
	int priority;
	UncaughtExceptionHandler exceptionHandler;
	boolean daemon;

	private boolean configured;

	private boolean wrapRunnable;// if acc is present wrap or keep it
	protected final AccessControlContext acc;

	// thread creation counter
	protected final AtomicLong counter = new AtomicLong();

	public ThreadFactoryX() {
		final Thread t = Thread.currentThread();
		ClassLoader loader;
		AccessControlContext acc = null;
		try {
			loader = t.getContextClassLoader();
			if (System.getSecurityManager() != null) {
				acc = AccessController.getContext();// keep current permissions
				acc.checkPermission(new RuntimePermission("setContextClassLoader"));
				acc = AccessController.getContext();// keep current permissions
				acc.checkPermission(new RuntimePermission("setContextClassLoader"));
			}
		} catch (SecurityException _skip) {
			// no permission
			loader = null;
			acc = null;
		}

		this.ccl = loader;
		this.acc = acc;
		this.priority = t.getPriority();
		this.daemon = true;// Executors have it false by default

		this.wrapRunnable = true;// by default wrap if acc is present
									// (+SecurityManager)

		// default pattern - caller className
		StackTraceElement[] stack = new Exception().getStackTrace();
		pattern(stack.length > 1 ? getOuterClassName(stack[1].getClassName()) : "ThreadFactoryX", true);
	}

	public ThreadFactory finishConfig() {
		configured = true;
		counter.addAndGet(0);// write fence "w/o" volatile
		return this;
	}

	public long getCreatedThreadsCount() {
		return counter.get();
	}

	protected void assertConfigurable() {
		if (configured)
			throw new IllegalStateException("already configured");
	}

	private static String getOuterClassName(String className) {
		int idx = className.lastIndexOf('.') + 1;
		className = className.substring(idx);// remove package
		idx = className.indexOf('$');
		if (idx <= 0) {
			return className;// handle classes starting w/ $
		}
		return className.substring(0, idx);// assume inner class

	}

	@Override
	public Thread newThread(Runnable r) {
		configured = true;
		final Thread t = new Thread(group, wrapRunnable(r), composeName(r), stackSize);
		t.setPriority(priority);
		t.setDaemon(daemon);
		t.setUncaughtExceptionHandler(exceptionHandler);// securityException
														// only if in the main
														// group, shall be safe
														// here
		// funny moment Thread.getUncaughtExceptionHandler() has a race.. badz
		// (can throw NPE)

		applyCCL(t);
		return t;
	}

	private void applyCCL(final Thread t) {
		if (ccl != null) {// use factory creator ACC for setContextClassLoader
			AccessController.doPrivileged(new PrivilegedAction<Object>() {
				@Override
				public Object run() {
					t.setContextClassLoader(ccl);
					return null;
				}
			}, acc);
		}
	}

	private Runnable wrapRunnable(final Runnable r) {
		if (acc == null || !wrapRunnable) {
			return r;
		}
		Runnable result = new Runnable() {
			public void run() {
				AccessController.doPrivileged(new PrivilegedAction<Object>() {
					@Override
					public Object run() {
						r.run();
						return null;
					}
				}, acc);
			}
		};
		return result;
	}

	protected String composeName(Runnable r) {
		return String.format(pattern, counter.incrementAndGet(), System.currentTimeMillis());
	}

	// standard setters allowing chaining, feel free to add normal setXXX
	public ThreadFactoryX pattern(String patten, boolean appendFormat) {
		assertConfigurable();
		if (appendFormat) {
			patten += ": %d @ %tF %<tT";// counter + creation time
		}
		this.pattern = patten;
		return this;
	}

	public ThreadFactoryX daemon(boolean daemon) {
		assertConfigurable();
		this.daemon = daemon;
		return this;
	}

	public ThreadFactoryX priority(int priority) {
		assertConfigurable();
		if (priority < Thread.MIN_PRIORITY || priority > Thread.MAX_PRIORITY) {// check
																				// before
																				// actual
																				// creation
			throw new IllegalArgumentException("priority: " + priority);
		}
		this.priority = priority;
		return this;
	}

	public ThreadFactoryX stackSize(long stackSize) {
		assertConfigurable();
		this.stackSize = stackSize;
		return this;
	}

	public ThreadFactoryX threadGroup(ThreadGroup group) {
		assertConfigurable();
		this.group = group;
		return this;
	}

	public ThreadFactoryX exceptionHandler(UncaughtExceptionHandler exceptionHandler) {
		assertConfigurable();
		this.exceptionHandler = exceptionHandler;
		return this;
	}

	public ThreadFactoryX wrapRunnable(boolean wrapRunnable) {
		assertConfigurable();
		this.wrapRunnable = wrapRunnable;
		return this;
	}

	public ThreadFactoryX ccl(ClassLoader ccl) {
		assertConfigurable();
		this.ccl = ccl;
		return this;
	}
}
