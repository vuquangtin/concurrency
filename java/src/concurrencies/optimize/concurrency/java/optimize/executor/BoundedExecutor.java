package concurrency.java.optimize.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
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
public final class BoundedExecutor implements DrainableExecutor {

    private final int maxTasks;

    private final Semaphore semaphore;

    private final Executor underlyingExecutor;

    private final Runnable acquire;

    private boolean drained = false;

    public BoundedExecutor(int maxTasks, BoundedStrategy onTasksExceeded, Executor underlyingExecutor) {
        this.maxTasks = maxTasks;
        this.semaphore = new Semaphore(maxTasks);
        this.underlyingExecutor = underlyingExecutor;
        this.acquire = onTasksExceeded == BoundedStrategy.BLOCK ? this::blockOnTasksExceeded : this::rejectOnTasksExceeded;
    }

    private void blockOnTasksExceeded() {
        semaphore.acquireUninterruptibly();
    }

    private void rejectOnTasksExceeded() {
        if (!semaphore.tryAcquire()) {
            throw new RejectedExecutionException("task limit of " + maxTasks + " exceeded");
        }
    }

    @Override
    public void execute(Runnable task) {
        Util.checkNotNull(task);
        synchronized (this) {
            if (drained) {
                throw new RejectedExecutionException("executor drained");
            } else {
                acquire.run();
            }
        }
        try {
            underlyingExecutor.execute(new KeyRunnable<>(
                    task,
                    () -> {
                        try {
                            task.run();
                        } finally {
                            semaphore.release();
                        }
                    })
            );
        } catch (RejectedExecutionException e) {
            semaphore.release();
            throw e;
        }
    }

    @Override
    public synchronized boolean drain(long timeout, TimeUnit unit) throws InterruptedException {
        if (!drained && semaphore.tryAcquire(maxTasks, timeout, unit)) {
            drained = true;
        }
        return drained;
    }
}