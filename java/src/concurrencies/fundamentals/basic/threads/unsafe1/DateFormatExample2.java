package basic.threads.unsafe1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

import basic.annotations.NotThreadSafe;

/**
 * @author panghu
 * @Title: DateFormatExample1
 * @Description: TODO
 * @date 19-2-20 上午11:16
 */
@Slf4j
@NotThreadSafe
public class DateFormatExample2 {
	static Logger logger = Logger.getLogger(DateFormatExample2.class.getName());
    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;


    public static void main(String[] args) throws Exception {
        logger.info("-------");
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update();
                    semaphore.release();
                } catch (Exception e) {
                    logger.error("exception", e);
                    logger.info("");
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    private static void update() {
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            simpleDateFormat.parse("20180208");
        } catch (ParseException e) {
            logger.error("parse exception",e);
        }
    }

}
