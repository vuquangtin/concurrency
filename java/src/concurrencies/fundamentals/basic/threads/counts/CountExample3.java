package basic.threads.counts;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import basic.annotations.NotThreadSafe;

/**
 * @author panghu
 * @Title: CountExample3
 * @ProjectName cuncurrency_demo
 * @Description: TODO
 * @date 19-2-16 下午3:09
 */
@Slf4j
@NotThreadSafe
public class CountExample3 {
	static Logger logger = Logger.getLogger(CountExample3.class.getName());
    /**请求总数*/
    public static int clientTotal = 5000;

    /**同事并发执行的线程数*/
    public static int threadTotal = 200;

    /**计数值*/
    public static int count = 0;

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0;i < clientTotal; i++){
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    System.out.println();
                    logger.error("exception",e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        logger.info("count:{}"+count);
    }

    private static synchronized void add(){
        count++;
    }
}