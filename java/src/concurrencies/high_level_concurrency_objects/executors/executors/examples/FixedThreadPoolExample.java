package executors.examples;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * newFixedThreadPool：new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>()
 *
 * 固定大小的线程，每提交一个任务就创建一次线程，直到线程数达到线程池的最大阈值。
 * 线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/19 23:09
 */
public class FixedThreadPoolExample extends Thread {

    private final Lock lock = new ReentrantLock();

    volatile AtomicInteger count = new AtomicInteger(0);


    @Override
    public void run() {
        lock.lock();
        // print count++
        System.out.println(String.format("%s [%d]: %d",Thread.currentThread().getName(),System.currentTimeMillis(),count.updateAndGet((v) -> v = v+1)));
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(100);
        final FixedThreadPoolExample fixedThreadExecutorExample = new FixedThreadPoolExample();

        // 向线程池中添加任务
        for (int i=0;i<1000;i++){
            service.execute(fixedThreadExecutorExample);
        }

        // 立即关闭线程池，此时子线程还在运行
        service.shutdown();

        Thread.sleep(10000);

        // reference : https://hacpai.com/article/1488023925829
        if (service.awaitTermination(5, TimeUnit.SECONDS)){
            System.out.println("Check ExecutorService status");
            service.shutdownNow();
        }

        System.out.println("Main thread exit.");

    }
}