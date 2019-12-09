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
 * @Title: CountExample4
 * @Description: TODO
 * @date 19-2-16 下午8:41
 */
@Slf4j
@NotThreadSafe
public class CountExample4 {
	static Logger logger = Logger.getLogger(CountExample4.class.getName());
    /**请求总数*/
    public static int clientTotal = 5000;

    /**同事并发执行的线程数*/
    public static int threadTotal = 200;

    /**计数值*/
    public static volatile int count = 0;

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0;i < clientTotal; i++){
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    add();
                    System.out.println();
                    semaphore.release();
                }catch (Exception e){
                    logger.error("exception",e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println();
        logger.info("count:{}"+count);
    }

    private static void add(){
        count++;
        /*
         * 1.从主存中获取最新的count值
         * 2.+1
         * 3.从工作内存写回主存
         */
    }
}

