package concurrency.java.optimize.utils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public final class ExecutorFactory {

    private ExecutorFactory() {}

    public static ThreadPoolExecutor newCachedThreadPool(String threadName) {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new NamedThreadFactory(threadName));
    }

    public static ThreadPoolExecutor newCachedThreadPool(long keepAliveTimeInSec, String threadName) {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, keepAliveTimeInSec, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new NamedThreadFactory(threadName));
    }

    /**
     * @link http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6458662
     */
    public static ThreadPoolExecutor newCachedThreadPool(int corePoolSize, long keepAliveTimeInSec, String threadName) {
        return new ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE, keepAliveTimeInSec, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new NamedThreadFactory(threadName));
    }

    public static ThreadPoolExecutor newCachedThreadPool(int corePoolSize, long keepAliveTimeInSec, String threadName, boolean daemon) {
        return new ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE, keepAliveTimeInSec, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new NamedThreadFactory(threadName, daemon));
    }

    /**
     * <code>ThreadPoolExecutor</code> only grows beyond coresize if your task queue is bounded and becomes full.
     * If your queue is unbounded then core is the limit. 
     * 
     * @see ThreadPoolExecutor
     * @link http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6458662
     */
    public static ThreadPoolExecutor newBoundedWorkQueueThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTimeInSec, String threadName) {
        final int workQueueSize = Math.min(corePoolSize + ((maximumPoolSize - corePoolSize) >> 1), corePoolSize << 1);
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTimeInSec, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(workQueueSize), new NamedThreadFactory(threadName), new WaitPolicy());
    }

    public static ThreadPoolExecutor newBoundedWorkQueueThreadPool(int corePoolSize, int maximumPoolSize, int workQueueSize, String threadName) {
        return newBoundedWorkQueueThreadPool(corePoolSize, maximumPoolSize, workQueueSize, 0L, threadName, false);
    }

    public static ThreadPoolExecutor newBoundedWorkQueueThreadPool(int corePoolSize, int maximumPoolSize, int workQueueSize, long keepAliveTimeInSec, String threadName, boolean daemon) {
        if(maximumPoolSize > corePoolSize && workQueueSize < maximumPoolSize) {
            throw new IllegalArgumentException("Pool never grows to maximumPoolSize ("
                    + maximumPoolSize + ") when workQueueSize (" + workQueueSize
                    + ") was less than corePoolSize (" + corePoolSize + ")");
        }
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTimeInSec, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(workQueueSize), new NamedThreadFactory(threadName, daemon), new WaitPolicy());
    }

    /**
     * Allow N active & W queued Thread.
     */
    public static ThreadPoolExecutor newBoundedWorkQueueFixedThreadPool(int nthreads, int workQueueSize, String threadName, boolean daemon) {
        return newBoundedWorkQueueThreadPool(nthreads, nthreads, workQueueSize, 0L, threadName, daemon);
    }

    /**
     * Allow N active & N queued Thread.
     */
    public static ThreadPoolExecutor newBoundedWorkQueueFixedThreadPool(int size, String threadName, boolean daemon) {
        return newBoundedWorkQueueFixedThreadPool(size, threadName, daemon, new WaitPolicy());
    }

    /**
     * Allow N active & N queued Thread.
     */
    public static ThreadPoolExecutor newBoundedWorkQueueFixedThreadPool(int size, String threadName, boolean daemon, RejectedExecutionHandler rejectHandler) {
        return new ThreadPoolExecutor(size, size, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(size), new NamedThreadFactory(threadName, daemon), rejectHandler);
    }

    public static ScheduledExecutorService newScheduledExecutor(int corePoolSize, String threadName) {
        return Executors.newScheduledThreadPool(corePoolSize, new NamedThreadFactory(threadName));
    }

    public static ExecutorService newSingleThreadExecutor(String threadName, boolean daemon) {
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory(threadName, daemon));
    }

    public static ThreadPoolExecutor newFixedThreadPool(int nThreads, String threadName) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory(threadName));
    }

    public static ThreadPoolExecutor newFixedThreadPool(int nThreads, String threadName, boolean daemon) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory(threadName, daemon));
    }

    public static ThreadPoolExecutor newFixedThreadPool(int nThreads, String threadName, int threadPriority, boolean daemon) {
        NamedThreadFactory factory = new NamedThreadFactory(threadName, daemon);
        factory.setPriority(threadPriority);
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), factory);
    }

    public static ThreadPoolExecutor newThreadPool(int corePoolSize, int maxPoolSize, String threadName) {
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory(threadName));
    }

    public static ThreadPoolExecutor newThreadPool(int corePoolSize, int maxPoolSize, long keepAliveInSec, String threadName) {
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveInSec, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory(threadName));
    }

    public static ThreadPoolExecutor newThreadPool(int corePoolSize, int maxPoolSize, long keepAliveInSec, String threadName, boolean daemon) {
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveInSec, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory(threadName, daemon));
    }

    /**
     * A handler for unexecutable tasks that waits until task can be submitted for execution.
     * Note that this blocking method can handle tricky scenarios such as calling shutdownNow() during an invokeAll().
     */
    public static final class WaitPolicy implements RejectedExecutionHandler {

        public WaitPolicy() {}

        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if(!executor.isShutdown()) {
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RejectedExecutionException(e);
                }
            }
        }
    }

}