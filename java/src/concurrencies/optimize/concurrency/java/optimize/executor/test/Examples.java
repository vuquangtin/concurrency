package concurrency.java.optimize.executor.test;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import concurrency.java.optimize.executor.BoundedStrategy;
import concurrency.java.optimize.executor.KeyRunnable;
import concurrency.java.optimize.executor.KeySequentialBoundedExecutor;
import concurrency.java.optimize.executor.KeySequentialExecutor;
import concurrency.java.optimize.executor.KeySequentialRunner;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Examples {

    private static final long timeout = 30;

    public static void basicExample() throws InterruptedException {
        ExecutorService underlyingExecutor = Executors.newFixedThreadPool(10);
        KeySequentialRunner<String> runner = new KeySequentialRunner<>(underlyingExecutor);

        String tradeIdA = "327";
        String tradeIdB = "831";
        // more Trade IDs can arrive in a real scenario, but it is usually not known how many upfront

        Runnable task = new Runnable() {
            @Override
            public void run() {
                // process a message for the trade
            }
        };

        runner.run(tradeIdA, task); // execute the task by the underlying executor

        runner.run(tradeIdB, task); // execution is not blocked by the task for tradeIdA

        runner.run(tradeIdA, task); // execution starts when the previous task for tradeIdA completes

        Executor executor = new KeySequentialExecutor(underlyingExecutor);

        Runnable runnable =
                new KeyRunnable<>(tradeIdA, task); // helper class delegating 'hashCode' and 'equals' to the key

        executor.execute(runnable);

        underlyingExecutor.shutdown();

        // at this point, tasks for new keys will be rejected; however, tasks for keys being currently executed may
        // still be accepted (and executed)

        underlyingExecutor.awaitTermination(timeout, TimeUnit.SECONDS);

        // if the executor terminates before a timeout, then it is guaranteed all accepted tasks have been executed
    }

    public static void boundedExecutorExample() throws InterruptedException {
        ExecutorService underlyingExecutor = Executors.newCachedThreadPool();
        int maxTasks = 10;
        KeySequentialBoundedExecutor boundedExecutor =
                new KeySequentialBoundedExecutor(maxTasks, BoundedStrategy.BLOCK, underlyingExecutor);

        KeyRunnable<String> task = new KeyRunnable<>("my key", () -> {
            // do something
        });

        boundedExecutor.execute(task);

        // execute more tasks ... at most 10 will be scheduled

        // before shutting down you can call a 'drain' method which blocks until all submitted task have been executed
        boundedExecutor.drain(timeout, TimeUnit.SECONDS); // returns true if drained; false if the timeout elapses

        // newly submitted tasks will be rejected after calling 'drain'

        underlyingExecutor.shutdownNow(); // safe to call 'shutdownNow' if drained as there should be no active tasks
    }
}