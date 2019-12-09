package executors.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * newCachedThreadPool:  new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>())
 * 创建一个可缓存的线程池
 * <p>
 * 如果线程池的大小超过了处理任务所需要的线程,会回收部分空闲（默认：60秒不执行任务）的线程
 * 当任务数增加时，此线程池又可以智能的添加新线程来处理任务
 * 此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/20 0:05
 */
public class CachedThreadPoolExample extends Thread {

    private final Lock lock = new ReentrantLock();

    volatile AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run() {
        lock.lock();

        System.out.println(Thread.currentThread().getName() + ": " + count.updateAndGet((v) -> v += 1));

        lock.unlock();
    }


    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        CachedThreadPoolExample cachedThreadPoolExample = new CachedThreadPoolExample();

        for (int i=0;i<100;i++){
            service.execute(cachedThreadPoolExample);
        }

        try {
            // 等待60S 后发现线程池中闲置线程被清空（没有WAITING状态的线程）
            Thread.sleep(61000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread exit.");


    }
}