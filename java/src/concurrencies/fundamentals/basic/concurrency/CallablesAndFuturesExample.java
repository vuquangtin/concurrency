package basic.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by xfan on 16/1/21.
 *
 * 除了 `Runnable` 类型的任务, executors 还支持另一种类型为 `Callable` 的任务. `Callable` 类型的任务可以有返回值.
 *
 * `Callable` 类型的任务像 `Runnable` 类型的任务一样可以提交到 executor 中去执行, 不同的是 `Callable` 类型的任务
 * 提交到 executor 后 executor 会返回一个类型为 `Future` 的对象, 我们可以用这个返回值去获得任务执行之后的返回结果.
 */
public class CallablesAndFuturesExample {

    public static void firstLookAtCallableTask() {
        Callable<Integer> callableTask = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return 123;
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(callableTask);

        System.out.println("future done? " + future.isDone());

        Integer result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            System.err.println("task interrupted");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("future done? " + future.isDone());
        System.out.println("result: " + result);

        CallablesAndFuturesExample.shutdownExecutor(executor);
    }

    /**
     * 调用 `future.get()` 会阻塞当前线程直到 future 对应的 `Callable` 任务执行结束.
     * 我们可以在通过 `Future` 获取结果时指定一个 timeout 值, 经过指定的时间后如果还没有
     * 获取到结果就会抛出 `TimeoutException`.
     */
    public static void timeoutWithFuture() {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<String> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return "Foo";
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        });

        try {
            String result = future.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new IllegalStateException("task interrupted", e);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.err.println("timeout!!!");
            e.printStackTrace();
        } finally {
            CallablesAndFuturesExample.shutdownExecutor(executor);
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


    public static void main(String[] args) {
        // CallablesAndFuturesExample.firstLookAtCallableTask();
        CallablesAndFuturesExample.timeoutWithFuture();
    }
}