package basic.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by xfan on 16/1/21.
 *
 * Executors 可以用来异步地执行任务, 通常它也管理着一个线程池, 所以我们不用去显式地创建线程.
 */
public class ExecutorExample {

    public static void firstLookAtExecutor() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String tname = Thread.currentThread().getName();
            System.out.printf("In thread \"%s\"", tname);
        });

        // Executor 必须要显式地去关闭, 否则它会一直处于监听新任务的状态.
        ExecutorExample.shutdownExecutor(executor);
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
        ExecutorExample.firstLookAtExecutor();
    }
}