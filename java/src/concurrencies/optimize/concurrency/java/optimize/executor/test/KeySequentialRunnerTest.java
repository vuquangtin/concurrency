package concurrency.java.optimize.executor.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import concurrency.java.optimize.executor.BoundedExecutor;
import concurrency.java.optimize.executor.BoundedStrategy;
import concurrency.java.optimize.executor.KeySequentialRunner;
import concurrency.java.optimize.executor.TaskExceptionHandler;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */


public class KeySequentialRunnerTest {

    @Test(timeout = 5000)
    public void executeTasksInCorrectOrder() throws InterruptedException {
        List<Integer> processed = Collections.synchronizedList(new LinkedList<>());
        CountDownLatch key1Latch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(1);

        Runnable key1Task1 = () -> {
            try {
                key1Latch.await();
            } catch (InterruptedException ignored) {
            }
            processed.add(1);
        };
        Runnable key1Task2 = () -> {
            processed.add(2);
            doneLatch.countDown();
        };
        Runnable key2Task1 = () -> processed.add(3);
        Runnable key2Task2 = () -> {
            processed.add(4);
            key1Latch.countDown();
        };

        ExecutorService underlyingExecutor = Executors.newFixedThreadPool(10);
        KeySequentialRunner<String> runner = new KeySequentialRunner<>(underlyingExecutor);
        runner.run("key1", key1Task1);
        runner.run("key1", key1Task2);
        runner.run("key2", key2Task1);
        runner.run("key2", key2Task2);

        doneLatch.await();

        assertEquals(3, processed.get(0).intValue());
        assertEquals(4, processed.get(1).intValue());
        assertEquals(1, processed.get(2).intValue());
        assertEquals(2, processed.get(3).intValue());

        underlyingExecutor.shutdownNow();
    }

    @Test(timeout = 5000)
    public void exceptionHandling() throws InterruptedException {
        LinkedBlockingQueue<Throwable> handledExceptions = new LinkedBlockingQueue<>();
        ExecutorService underlyingExecutor = Executors.newFixedThreadPool(10);
        KeySequentialRunner<String> runner = new KeySequentialRunner<>(
                underlyingExecutor,
                new TaskExceptionHandler<String>() {
                    @Override
                    public void onException(String key, Throwable t) {
                        handledExceptions.offer(new Throwable(key, t));
                    }
                }
        );
        RuntimeException exception1 = new RuntimeException("test1");
        RuntimeException exception2 = new RuntimeException("test2");

        runner.run("key", () -> {
            throw exception1;
        });
        runner.run("key", () -> {
            throw exception2;
        });

        Throwable handled1 = handledExceptions.take();
        Throwable handled2 = handledExceptions.take();

        assertEquals(exception1, handled1.getCause());
        assertEquals("key", handled1.getMessage());
        assertEquals(exception2, handled2.getCause());
        assertEquals("key", handled2.getMessage());

        underlyingExecutor.shutdownNow();
    }

    @Test(timeout = 5000)
    public void taskExecutionDuringShutdown() throws Exception {
        ExecutorService underlyingExecutor = Executors.newFixedThreadPool(10);
        KeySequentialRunner<String> runner = new KeySequentialRunner<>(underlyingExecutor);
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);

        Runnable key1Task1 = () -> {
            try {
                latch1.await();
            } catch (InterruptedException ignored) {
            }
            queue.offer(1);
        };
        Runnable key1Task2 = () -> {
            queue.offer(2);
            latch2.countDown();
        };
        Runnable key2Task1 = () -> {
            try {
                latch2.await();
            } catch (InterruptedException ignored) {
            }
            queue.offer(3);
        };
        Runnable key2Task2 = () -> queue.offer(4);
        Runnable key2Task3 = () -> {
            try {
                runner.run("key2", TestUtil.doSomething);
            } catch (RejectedExecutionException e) {
                queue.offer(5);
            }
        };

        runner.run("key2", key2Task1);
        runner.run("key2", key2Task2);
        runner.run("key1", key1Task1);
        runner.run("key1", key1Task2);

        underlyingExecutor.shutdown();

        runner.run("key2", key2Task3);

        try {
            runner.run("key3", TestUtil.doSomething);
            fail("not rejected");
        } catch (RejectedExecutionException e) {
            assertTrue(true);
        } catch (Throwable t) {
            fail("invalid exception");
        }

        latch1.countDown();

        underlyingExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

        try {
            runner.run("key1", TestUtil.doSomething);
            fail("not rejected");
        } catch (RejectedExecutionException e) {
            assertTrue(true);
        } catch (Throwable t) {
            fail("invalid exception");
        }

        assertEquals(1, queue.take().intValue());
        assertEquals(2, queue.take().intValue());
        assertEquals(3, queue.take().intValue());
        assertEquals(4, queue.take().intValue());
        assertEquals(5, queue.take().intValue());

        Field keyRunners = KeySequentialRunner.class.getDeclaredField("keyRunners");
        keyRunners.setAccessible(true);
        assertTrue(((Map<?, ?>) keyRunners.get(runner)).isEmpty());
    }

    @Test(timeout = 5000, expected = NullPointerException.class)
    public void throwExceptionWhenTaskIsNull() {
        ExecutorService underlying = Executors.newCachedThreadPool();
        KeySequentialRunner<Integer> runner = new KeySequentialRunner<>(underlying);

        try {
            runner.run(1, null);
        } finally {
            underlying.shutdownNow();
        }
    }

    @Test(timeout = 5000)
    public void underLoad() throws InterruptedException {
        ExecutorService underlyingExecutor = Executors.newFixedThreadPool(10);
        KeySequentialRunner<Integer> runner = new KeySequentialRunner<>(underlyingExecutor);
        List<Integer> processed = Collections.synchronizedList(new LinkedList<>());

        for (int i = 0; i < 1000; ++i) {
            final int toProcess = i;
            runner.run(i % 2, () -> processed.add(toProcess));
        }

        underlyingExecutor.shutdown();
        underlyingExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

        int previousOdd = -1;
        int previousEven = -2;
        for (int p : processed) {
            if (p % 2 == 0) {
                assertEquals(previousEven + 2, p);
                previousEven = p;
            } else {
                assertEquals(previousOdd + 2, p);
                previousOdd = p;
            }
        }
    }

    @Test(timeout = 5000)
    public void underLoadWithBoundedExecutor() throws InterruptedException {
        ExecutorService underlyingExecutor = Executors.newFixedThreadPool(10);
        BoundedExecutor boundedExecutor = new BoundedExecutor(1,BoundedStrategy. BLOCK, underlyingExecutor);
        KeySequentialRunner<Integer> runner = new KeySequentialRunner<>(boundedExecutor);
        List<Integer> processed = Collections.synchronizedList(new LinkedList<>());

        for (int i = 0; i < 1000; ++i) {
            final int toProcess = i;
            runner.run(i % 2, () -> processed.add(toProcess));
        }

        boundedExecutor.drain(Long.MAX_VALUE, TimeUnit.SECONDS);
        underlyingExecutor.shutdownNow();

        int previousOdd = -1;
        int previousEven = -2;
        for (int p : processed) {
            if (p % 2 == 0) {
                assertEquals(previousEven + 2, p);
                previousEven = p;
            } else {
                assertEquals(previousOdd + 2, p);
                previousOdd = p;
            }
        }
    }
}