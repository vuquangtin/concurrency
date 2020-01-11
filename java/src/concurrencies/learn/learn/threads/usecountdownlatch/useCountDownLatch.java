package learn.threads.usecountdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch实现多线程执行完毕，再执行别的线程
 *      https://blog.csdn.net/zhutulang/article/details/48504487
 */
public class useCountDownLatch {
    public static void main2(String[] args) throws InterruptedException {
        int threadNum = 3;//线程数目
        final CountDownLatch latch = new CountDownLatch(threadNum);
        for(int i=0;i<threadNum;i++){
            new Thread(){
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName()+"  开始执行存储过程..");
                        Thread.sleep(2000);
                        System.out.println(Thread.currentThread().getName()+"  存储过程执行完毕...");
                        //2、子线程执行完毕，计数减1
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        System.out.println("等待子线程执行完毕...");
        //3、 当前线程挂起等待
        latch.await();
        System.out.println("主线程执行完毕....");
    }

    public static void main(String[] args) throws InterruptedException {
        latchTest();
    }

    /**
     * 并发测试
     * @throws InterruptedException
     */
    private static void latchTest() throws InterruptedException {
        int poolSize = 100;
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(poolSize);
        ExecutorService exce = Executors.newFixedThreadPool(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        start.await();
                        testLoad(num);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        end.countDown();
                    }
                }
            };
            exce.submit(run);
        }
        start.countDown();
        end.await();
        exce.shutdown();
    }
    private static int num = 100;
    private static void testLoad(int num){
        System.out.println(num--);
    }
}
