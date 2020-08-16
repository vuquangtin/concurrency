package concurrency.java.optimize.google;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BoundedCachedExecutor extends BoundedExecutor
{
    private final ExecutorService executor;

    public BoundedCachedExecutor(int maxThreads)
    {
        this(Executors.defaultThreadFactory(), maxThreads);
    }

    public BoundedCachedExecutor(int maxThreads, int maxQueued)
    {
        this(Executors.defaultThreadFactory(), maxThreads, maxQueued);
    }

    public BoundedCachedExecutor(ThreadFactory threadFactory, int maxThreads)
    {
        this(Executors.newCachedThreadPool(threadFactory), maxThreads);
    }

    public BoundedCachedExecutor(ThreadFactory threadFactory, int maxThreads, int maxQueued)
    {
        this(Executors.newCachedThreadPool(threadFactory), maxThreads, maxQueued);
    }

    protected BoundedCachedExecutor(ExecutorService executor, int maxThreads)
    {
        super(executor, maxThreads);
        this.executor = executor;
    }

    protected BoundedCachedExecutor(ExecutorService executor, int maxThreads, int maxQueued)
    {
        super(executor, maxThreads, maxQueued);
        this.executor = executor;
    }

    @Override
    public void shutdown()
    {
        super.shutdown();
        executor.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow()
    {
        List<Runnable> runnables = super.shutdownNow();
        executor.shutdown();
        return runnables;
    }
}