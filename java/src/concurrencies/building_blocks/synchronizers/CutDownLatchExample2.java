package synchronizers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * @author panghu
 * @Title: CutDownLatchExample1
 * @ProjectName example
 * @Description: TODO
 * @date 19-2-26 上午9:42
 */
@Slf4j
public class CutDownLatchExample2 {
	static Logger logger = Logger.getLogger(CutDownLatchExample2.class.getName());
    private static int threadCount = 200;

    public static void main(String[] args) throws Exception{

        ExecutorService executorService = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            //Thread.sleep(1);  这一步是没有作用的  不太懂
            executorService.execute(()->{
                try{
                    test(threadNum);
                }catch (Exception e){
                    logger.error("Exception:{}",e);
                }finally {
                    //根据需求放在不同的地方，进行减一操作
                    //为了能够执行，一般是放在finally代码块中
                    countDownLatch.countDown();
                }
            });
        }
        //await()保证线程执行完，需要等计数器减到0才能执行。也可以指定等待的时间
        countDownLatch.await(10, TimeUnit.MILLISECONDS);
        logger.info("finish");
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        Thread.sleep(100);
        logger.info("{}"+threadNum);
    }

}
