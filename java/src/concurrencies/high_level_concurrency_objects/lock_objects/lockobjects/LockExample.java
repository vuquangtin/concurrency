package lockobjects;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

@Slf4j
public class LockExample {
	static Logger logger = Logger.getLogger(LockExample.class.getName());
    private static int count = 0;
    private final static Lock lock = new ReentrantLock();

    private static int threadNum = 200;
    private static int clientNum = 5000;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadNum);
        final CountDownLatch countDownLatch = new CountDownLatch(clientNum);

        for (int i = 0; i < clientNum; i++){
            exec.execute(()->{
                try{
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    System.out.println(e);
                }
                countDownLatch.countDown();
            });

        }
        countDownLatch.await();
        exec.shutdown();
        logger.info("{}"+count);
    }

    private static void add() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

}
