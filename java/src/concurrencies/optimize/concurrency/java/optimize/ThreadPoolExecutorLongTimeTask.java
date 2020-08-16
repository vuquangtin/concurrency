package concurrency.java.optimize;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadPoolExecutorLongTimeTask {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10), r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }, new ThreadPoolExecutor.AbortPolicy());

        IntStream.range(0,10).boxed().forEach(i->{
            executorService.execute(()->{
                while (true) {

                }
            });
        });

        executorService.shutdownNow();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }
}