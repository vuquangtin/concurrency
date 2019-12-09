package executors.examples;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * newSingleThreadExecutor：new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>())
 *
 * 线程池中只有一个工作线程，它依次执行每个任务
 * 如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它
 * 此线程池保证所有任务的执行顺序按照任务的提交顺序执行
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/19 22:43
 */
public class SingleThreadExecutorExample extends Thread {

    private final Random random = ThreadLocalRandom.current();



    private final AtomicInteger count = new AtomicInteger(0);

    void randomWait() {
        try {
            sleep((long) (3000 * random.nextDouble()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        for (;;) {
            System.out.println(Thread.currentThread().getName() + ": " + count.getAndUpdate((v) -> v = v + 1));
            // 当计数器达到100时退出
            if (100 == count.get()) {
                break;
            }
        }

        System.out.println(Thread.currentThread().getName() + " : done.");
    }

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(new SingleThreadExecutorExample());
        Thread.sleep(3000);

        System.out.println(">>>>>>>>>");
        service.execute(new SingleThreadExecutorExample());
        Thread.sleep(3000);

        System.out.println(">>>>>>>>>");
        service.submit(new SingleThreadExecutorExample());
        Thread.sleep(3000);

        // close SingleThreadExecutor
        service.shutdown();

        System.out.println("Main Thead exit.");
    }
}