package concurrency.java.wrappers;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public final class NessExecutors
{

    private NessExecutors() { }

    /**
     * Wrap an {@link ExecutorService} to make it appropriate for use in a
     * try-with-resources block.  The service is shutdown but may not be terminated
     * by the end of the try statement.
     */
    public static ShutdownExecutorService autoShutdown(ExecutorService service)
    {
        return new ShutdownExecutorService(service);
    }

    /**
     * Wrap an {@link ExecutorService} to make it appropriate for use in a
     * try-with-resources block.  The service is terminated at the end of the
     * try statement unless the timeout elapses, in which case a TimeoutException is thrown.
     */
    public static TerminatingExecutorService autoTerminate(ExecutorService service, int timeout, TimeUnit units)
    {
        return new TerminatingExecutorService(service, timeout, units);
    }

    /**
     * Wrap a {@link ScheduledExecutorService} to make it appropriate for use in a
     * try-with-resources block.  The service is shutdown but may not be terminated
     * by the end of the try statement.
     */
    public static ShutdownScheduledExecutorService autoShutdown(ScheduledExecutorService service)
    {
        return new ShutdownScheduledExecutorService(service);
    }

    /**
     * Wrap a {@link ScheduledExecutorService} to make it appropriate for use in a
     * try-with-resources block.  The service is terminated at the end of the
     * try statement unless the timeout elapses, in which case a TimeoutException is thrown.
     */
    public static TerminatingScheduledExecutorService autoTerminate(ScheduledExecutorService service, int timeout, TimeUnit units)
    {
        return new TerminatingScheduledExecutorService(service, timeout, units);
    }

    /**
     * Invoke all of the given callables.  If they all succeed, returns a list of the futures.  All will be
     * {@link Future#isDone()}.  If any fails, returns the list of Futures that succeeded before the failure, and
     * the final future that caused the computation to fail.  The remaining futures will be cancelled.
     * If the calling thread is interrupted, it will make a best-effort attempt to cancel running tasks.
     */
    public static <T> List<Future<T>> invokeAllExplosively(ExecutorService service, Collection<? extends Callable<T>> tasks)
    throws InterruptedException
    {
        final ExecutorCompletionService<T> completionService = new ExecutorCompletionService<>(service);
        final ImmutableList.Builder<Future<T>> results = ImmutableList.builder();
        final Set<Future<T>> inFlight = Sets.newHashSetWithExpectedSize(tasks.size());

        boolean interrupted = false;

        for (Callable<T> task : tasks) {
            inFlight.add(completionService.submit(task));
        }

        while (!inFlight.isEmpty()) {
            final Future<T> future;
            try {
                future = completionService.take();
            } catch (InterruptedException e) {
                interrupted = true;
                break;
            }

            inFlight.remove(future);
            results.add(future);

            try {
                future.get();
            } catch (InterruptedException e) {
                interrupted = true;
                break;
            } catch (ExecutionException e) {
                break;
            }
        }

        for (final Future<T> future : inFlight) {
            future.cancel(true);
        }

        if (interrupted) {
            throw new InterruptedException();
        }

        return results.build();
    }
}