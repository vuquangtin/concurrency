package concurrency.java.optimize;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
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
public class ThreadPoolExecutorBuild {

    public static ExecutorService buildThreadPoolExecutor() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1), r -> {
            Thread thread = new Thread(r);
            return thread;
        }, new ThreadPoolExecutor.AbortPolicy());

        executorService.execute(() -> sleepSeconds(100));
        executorService.execute(() -> sleepSeconds(100));
        executorService.execute(() -> sleepSeconds(100));
        executorService.execute(() -> sleepSeconds(100));

        return executorService;
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) buildThreadPoolExecutor();
        int activeCount = -1;
        int queueSize = -1;
        while (true) {
            if (activeCount != executor.getActiveCount() || queueSize != executor.getQueue().size()) {
                System.out.println("activeCount:" + executor.getActiveCount());
                System.out.println("coreSize:" + executor.getCorePoolSize());
                System.out.println("queueSize:" + executor.getQueue().size());
                System.out.println("maxSize:" + executor.getMaximumPoolSize());
                System.out.println("-----------------------------------------------");
            }
            activeCount = executor.getActiveCount();
            queueSize = executor.getQueue().size();
        }

    }

    public static void sleepSeconds(long seconds) {
        try {
            System.out.println(Thread.currentThread().getName() + "**");
            Thread.sleep(seconds);
//            System.out.println(Thread.currentThread().getName()+" finish....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

