package concurrency.java.optimize.executor.test;

import static org.junit.Assert.*;
import static concurrency.java.optimize.executor.BoundedStrategy.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import concurrency.java.optimize.executor.BoundedExecutor;
import concurrency.java.optimize.executor.KeyRunnable;
import concurrency.java.optimize.executor.KeySequentialExecutor;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BoundedExecutorTest {

    @Test(timeout = 5000)
    public void blockWhenLimitReached() throws InterruptedException {
        ExecutorService underlyingExecutor = Executors.newFixedThreadPool(10);
        KeySequentialExecutor keySequentialExecutor = new KeySequentialExecutor(underlyingExecutor);
        AtomicInteger completed = new AtomicInteger(0);
        CountDownLatch block = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(1);
        Runnable blockingTask = new KeyRunnable<>("key", () -> {
            try {
                block.await();
            } catch (InterruptedException ignored) {
            }
            completed.incrementAndGet();
        });
        Runnable simpleTask = new KeyRunnable<>("key", completed::incrementAndGet);

        BoundedExecutor bounded = new BoundedExecutor(5, BLOCK, keySequentialExecutor);
        bounded.execute(blockingTask);
        bounded.execute(simpleTask);
        bounded.execute(simpleTask);
        bounded.execute(simpleTask);
        bounded.execute(simpleTask);

        Thread t = new Thread(() -> {
            bounded.execute(simpleTask);
            done.countDown();
        });
        t.start();

        assertFalse(done.await(1, TimeUnit.SECONDS));

        block.countDown();
        done.await();

        underlyingExecutor.shutdown();
        underlyingExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

        assertEquals(6, completed.get());
    }

    @Test(timeout = 5000, expected = RejectedExecutionException.class)
    public void rejectWhenLimitReached() {
        ExecutorService underlyingExecutor = Executors.newSingleThreadExecutor();
        CountDownLatch block = new CountDownLatch(1);
        Runnable blockingTask = () -> {
            try {
                block.await();
            } catch (InterruptedException ignored) {
            }
        };

        BoundedExecutor bounded = new BoundedExecutor(2, REJECT, underlyingExecutor);
        bounded.execute(blockingTask);
        bounded.execute(blockingTask);

        try {
            bounded.execute(blockingTask);
        } finally {
            underlyingExecutor.shutdownNow();
        }
    }

    @Test(timeout = 10000)
    public void drain() throws InterruptedException {
        for (int i = 0; i < 1000; ++i) {
            ExecutorService underlying = Executors.newFixedThreadPool(5);
            BoundedExecutor bounded = new BoundedExecutor(20, BLOCK, underlying);
            CountDownLatch latch = new CountDownLatch(1);
            AtomicInteger completed = new AtomicInteger(0);

            for (int j = 0; j < 10; ++j) {
                if (j == 5) {
                    bounded.execute(() -> {
                        try {
                            latch.await();
                            completed.incrementAndGet();
                        } catch (InterruptedException ignored) {
                        }
                    });
                } else {
                    bounded.execute(completed::incrementAndGet);
                }
            }

            assertFalse(bounded.drain(1, TimeUnit.MILLISECONDS));

            latch.countDown();

            assertTrue(bounded.drain(Long.MAX_VALUE, TimeUnit.SECONDS));
            assertEquals(10, completed.get());
            assertTrue(underlying.shutdownNow().isEmpty());
        }
    }

    @Test(timeout = 5000, expected = RejectedExecutionException.class)
    public void rejectTasksAfterDrain() throws InterruptedException {
        ExecutorService underlying = Executors.newCachedThreadPool();
        BoundedExecutor bounded = new BoundedExecutor(10, BLOCK, underlying);

        bounded.execute(TestUtil.doSomething);

        bounded.drain(Long.MAX_VALUE, TimeUnit.SECONDS);
        try {
            bounded.execute(TestUtil.doSomething);
        } finally {
            underlying.shutdownNow();
        }
    }

    @Test(timeout = 5000)
    public void safeToCallDrainMultipleTime() throws InterruptedException {
        ExecutorService underlying = Executors.newCachedThreadPool();
        BoundedExecutor bounded = new BoundedExecutor(10, BLOCK, underlying);

        bounded.execute(TestUtil.doSomething);

        assertTrue(bounded.drain(Long.MAX_VALUE, TimeUnit.SECONDS));
        assertTrue(bounded.drain(Long.MAX_VALUE, TimeUnit.SECONDS));

        underlying.shutdownNow();
    }

    @Test(timeout = 5000)
    public void releaseLockOnException() {
        Executor underlying = new Executor() {

            private int count = 0;

            @Override
            public void execute(Runnable command) {
                if (count++ == 0) {
                    throw new RejectedExecutionException();
                }
                command.run();
            }
        };

        Executor bounded = new BoundedExecutor(1, BLOCK, underlying);

        boolean thrown = false;
        try {
            bounded.execute(TestUtil.doSomething);
        } catch (RejectedExecutionException e) {
            thrown = true;
        }
        bounded.execute(TestUtil.doSomething);

        assertTrue(thrown);
    }

    @Test(expected = NullPointerException.class)
    public void throwExceptionWhenTaskIsNull() {
        ExecutorService underlying = Executors.newCachedThreadPool();
        Executor bounded = new BoundedExecutor(10, BLOCK, underlying);

        try {
            bounded.execute(null);
        } finally {
            underlying.shutdownNow();
        }
    }
}