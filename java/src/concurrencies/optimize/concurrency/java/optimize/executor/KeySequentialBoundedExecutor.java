package concurrency.java.optimize.executor;

import java.util.concurrent.Executor;
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
public final class KeySequentialBoundedExecutor implements DrainableExecutor {

    private final BoundedExecutor boundedExecutor;

    public KeySequentialBoundedExecutor(int maxTasks, BoundedStrategy onTasksExceeded, Executor underlyingExecutor) {
        boundedExecutor = new BoundedExecutor(maxTasks, onTasksExceeded, new KeySequentialExecutor(underlyingExecutor));
    }

    @Override
    public void execute(Runnable task) {
        boundedExecutor.execute(task);
    }

    @Override
    public boolean drain(long timeout, TimeUnit unit) throws InterruptedException {
        return boundedExecutor.drain(timeout, unit);
    }
}