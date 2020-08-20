package concurrency.java.wrappers;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
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
public abstract class DelegatingScheduledExecutorService extends DelegatingExecutorService implements ScheduledExecutorService
{
    private final ScheduledExecutorService delegate;

    DelegatingScheduledExecutorService(ScheduledExecutorService delegate)
    {
        super(delegate);
        this.delegate = delegate;
    }

    // DELEGATES

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit)
    {
        return delegate.schedule(command, delay, unit);
    }

    @Override
    public void execute(Runnable command)
    {
        delegate.execute(command);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit)
    {
        return delegate.schedule(callable, delay, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
    {
        return delegate.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit)
    {
        return delegate.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }
}