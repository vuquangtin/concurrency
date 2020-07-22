package basic.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by xfan on 16/1/22.
 */
public class ScheduledExecutorExample {

    public static void executorTaskWithDelay() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new IllegalStateException("task interrupted", e);
        }

        long remainingDelay = future.getDelay(TimeUnit.SECONDS);
        System.out.printf("Remaining delay: %ss%n", remainingDelay);

        shutdownExecutor(executor);
    }

    /**
     * 使用方法 `scheduleAtFixedRate()` 时要注意: 如果 task 的执行时间比 task 之间的间隔时间要长,
     * 那么后续的 task 会推迟执行, 不会并发执行.
     */
    public static void executeTaskPeriodicallyAtFixedRate() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };

        int initialDelay = 0;
        int period = 1;

        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    /**
     * `scheduleWithFixedDelay()` 方法里的 delay 为上一个 task 的结束时间和下一个 task 的开始时间之间的间隔.
     */
    public static void executeTaskPeriodicallyWithFixedDelay() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };

        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
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

    public static void main(String[] args) {
        System.out.println("- - - executeTaskWithDelay() - - -");
        // ScheduledExecutorExample.executorTaskWithDelay();

        System.out.println("- - - executeTaskPeriodicallyAtFixedRate() - - -");
        // ScheduledExecutorExample.executeTaskPeriodicallyAtFixedRate();

        System.out.println("- - - executeTaskPeriodicallyWithFixedDelay() - - -");
        ScheduledExecutorExample.executeTaskPeriodicallyWithFixedDelay();
    }
}