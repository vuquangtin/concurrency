package concurrency.java.optimize;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
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
public final class BlockingExecutor { 

    private final Executor executor;
    private final Semaphore semaphore;

    public BlockingExecutor(int queueSize, int corePoolSize, int maxPoolSize, int keepAliveTime, TimeUnit unit, ThreadFactory factory) {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        this.executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, unit, queue, factory);
        this.semaphore = new Semaphore(queueSize + maxPoolSize);
    }

    private void execImpl (final Runnable command) throws InterruptedException {
        semaphore.acquire();
        try {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        command.run();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            // will never be thrown with an unbounded buffer (LinkedBlockingQueue)
            semaphore.release();
            throw e;
        }
    }

    public void execute (Runnable command) throws InterruptedException {
        execImpl(command);
    }
}


