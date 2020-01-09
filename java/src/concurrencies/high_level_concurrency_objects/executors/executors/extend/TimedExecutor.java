package executors.extend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import concurrencies.utilities.BotLogger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class TimedExecutor extends ThreadPoolExecutor {
	private static final Logger logger = BotLogger.get(TimedExecutor.class);
	private static final int CORE_POOL_SIZE = 0, MAX_POOL_SIZE = Integer.MAX_VALUE;
	private static final long KEEP_ALIVE_TIME = 60L;
	private static final boolean INTERRUPT = true;

	public final Map<Integer, Long> times = new ConcurrentHashMap<>(); // maps
																		// future
																		// hashcode
																		// to
																		// time
	public final Map<Integer, Future<Void>> tasks = new ConcurrentHashMap<>();
	public final Map<Integer, OnExecutionEndListener> listeners = new ConcurrentHashMap<>();

	public TimedExecutor() {
		// follows the same constructor initialisation as a CachedThreadPool
		// executor
		super(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
	}

	/** start timing for each thread */
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		startTime(r);
	}

	/**
	 * Override afterExecute to be able to : - propagate the runnable's
	 * exceptions to the main thread - end timing - remove thread from running
	 * threads list
	 */
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		endTime(r);
		if (r != null) { // we did not get interrupted
			OnExecutionEndListener listener = listeners.get(r.hashCode());
			if (listener != null)
				listener.onExecutionEnd(times.get(r.hashCode()));
			else
				logger.error("Listener for " + r + " is null ? which command created this future then =v");
			cleanup(r);
		}
		if (t == null && r instanceof Future<?>) { // handle normal execution
			try {
				Future<?> future = (Future<?>) r;
				if (Thread.interrupted() && !future.isDone())
					throw new InterruptedException(Thread.currentThread().getName() + " had been interrupted.");
				else if (future.isDone() && !future.isCancelled())
					future.get();
			} catch (CancellationException e) {
				t = e;
			} catch (ExecutionException e) {
				t = e.getCause();
			} catch (InterruptedException e) {
				t = e;
				Thread.currentThread().interrupt();
			}
		}
		if (t != null && t instanceof Exception && !Thread.interrupted()) { // handle
																			// propagation+interruption
			if (!isShutdown()) {
				logger.error("Propagated exception from callable : " + t, t);
				Arrays.asList(t.getStackTrace()).forEach((trace) -> logger.error(trace.toString()));
			}
		}
	}

	private void cleanup(Runnable r) {
		times.remove(r.hashCode());
		listeners.remove(r.hashCode());
		tasks.remove(r.hashCode());
	}

	private void startTime(Runnable r) {
		times.put(r.hashCode(), System.currentTimeMillis());
	}

	private void endTime(Runnable r) {
		long duration = System.currentTimeMillis() - times.get(r.hashCode());
		times.put(r.hashCode(), duration);
	}

	public Collection<OnExecutionEndListener> currentlyRunningTasks() {
		return Collections.unmodifiableCollection(listeners.values());
	}

	public boolean isRunning(OnExecutionEndListener command) {
		for (OnExecutionEndListener callable : listeners.values())
			if (callable.equals(command))
				return true;
		return false;
	}

	public OnExecutionEndListener getListener(int id) {
		for (Entry<Integer, OnExecutionEndListener> entry : listeners.entrySet())
			if (entry.getKey().equals(id))
				return entry.getValue();
		return null;
	}

	public int getId(Future<Void> future) {
		for (Entry<Integer, Future<Void>> entry : tasks.entrySet())
			if (entry.getValue().equals(future))
				return entry.getKey();
		return -1;
	}

	public int getId(OnExecutionEndListener listener) {
		for (Entry<Integer, OnExecutionEndListener> entry : listeners.entrySet())
			if (entry.getValue().equals(listener))
				return entry.getKey();
		return -1;
	}

	synchronized public void killAll() {
		for (Future<Void> task : tasks.values())
			task.cancel(INTERRUPT);
	}

	synchronized public List<OnExecutionEndListener> killAllExcept(OnExecutionEndListener requester) {
		List<OnExecutionEndListener> abortedCmds = new ArrayList<>();
		for (Entry<Integer, Future<Void>> entry : tasks.entrySet()) {
			if (!entry.getKey().equals(getId(requester)) && !entry.getValue().isDone()) { // skip
																							// requester
																							// thread
				abortedCmds.add(listeners.get(entry.getKey()));
				entry.getValue().cancel(INTERRUPT);
			}
		}
		return abortedCmds;
	}

	synchronized public List<OnExecutionEndListener> killAllExcept(Class<?> type) {
		List<OnExecutionEndListener> abortedCmds = new ArrayList<>();
		OnExecutionEndListener listener;
		Future<Void> future;
		for (Entry<Integer, Future<Void>> entry : tasks.entrySet()) {
			listener = listeners.get(entry.getKey());
			future = entry.getValue();
			if (type.isInstance(listener.getClass()) && !future.isDone()) { // kill
																			// only
																			// specified
																			// type
				abortedCmds.add(listener);
				future.cancel(INTERRUPT);
			}
		}
		return abortedCmds;
	}
}
