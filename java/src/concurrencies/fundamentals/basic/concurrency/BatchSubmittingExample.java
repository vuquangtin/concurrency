package basic.concurrency;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by xfan on 16/1/21.
 */
public class BatchSubmittingExample {

    /**
     *
     */
    public static void batchSubmittingViaInvokeAll() {
        /* `newWorkStealingPool()` is part of Java 8 and returns an executor of
           type ForkJoinPool which works slightly different than normal executors.
           Instead of using a fixed size thread-pool ForkJoinPools are created for
           a given parallelism size which per default is the number of available cores of the hosts CPU.*/
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> tasks = Arrays.asList(
                () -> "task 1",
                () -> "task 2",
                () -> "task 3"
        );

        try {
            List<Future<String>> futures = executor.invokeAll(tasks);

            futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);

        } catch (InterruptedException e) {
            System.err.println("task interrupted");
        } finally {
            BatchSubmittingExample.shutdownExecutor(executor);
        }
    }

    /**
     * 不同于 `invokeAll()`, `invokeAny()` 会阻塞当前线程直到有一个 callable 执行完毕,
     * 它的返回值是这个已完成的 callable 的返回结果.
     */
    public static void batchSubmittingViaInvokeAny() {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                callable("task 4", 2),
                callable("task 5", 1),
                callable("task 6", 3)
        );

        try {
            String result = executor.invokeAny(callables);
            System.out.println("result: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            shutdownExecutor(executor);
        }
    }

    public static void shutdownExecutor(ExecutorService executor) {
        System.out.println("attempt to shutdown executor");

        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("task interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    public static Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }

    public static void main(String[] args) {
        System.out.println("- - - invokeAll() - - -");
        BatchSubmittingExample.batchSubmittingViaInvokeAll();

        System.out.println(" - - - invokeAny() - - -");
        BatchSubmittingExample.batchSubmittingViaInvokeAny();
    }
}