package executors.examples;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * newScheduledThreadPool:
 * <p>
 * 创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/20 0:19
 */
public class ScheduledThreadPoolExample extends Thread {

    volatile AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + ": " + count.updateAndGet((v) -> v += 1));
        }
    }

    public static void main(String[] args) {
        final ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        final ScheduledThreadPoolExample scheduledThreadPoolExample = new ScheduledThreadPoolExample();

        // 延迟1s执行
        service.schedule(scheduledThreadPoolExample, 1, TimeUnit.SECONDS);

        // 延迟2s执行，每5s执行一次
        service.scheduleAtFixedRate(scheduledThreadPoolExample, 2, 5, TimeUnit.SECONDS);

    }
}