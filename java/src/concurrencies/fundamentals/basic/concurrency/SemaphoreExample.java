package basic.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by xfan on 16/2/23.
 */
public class SemaphoreExample {

    public void limitAccessToALongRunningTask() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Semaphore semaphore = new Semaphore(5);

        Runnable longRunningTask = () -> {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);

                if (permit) {
                    System.out.println("Semaphore acquired");
                    TimeUnit.SECONDS.sleep(5);
                } else {
                    System.out.println("Could not acquire semaphore");
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                if (permit) {
                    semaphore.release();
                }
            }
        };

        IntStream.range(0, 10)
                .forEach(i -> executor.submit(longRunningTask));

        stopExecutor(executor);
    }

    private void stopExecutor(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        } finally {
            if (!executor.isTerminated()) {
                executor.shutdownNow();
            }
        }
    }

    public static void main(String[] args) {
        SemaphoreExample example = new SemaphoreExample();
        example.limitAccessToALongRunningTask();
    }
}