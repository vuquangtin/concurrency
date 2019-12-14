package synchronizers.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * @author panghu
 * @Title: CycliBarrierExample1
 * @ProjectName example
 * @Description: TODO
 * @date 19-2-26 上午11:38
 */
@Slf4j
public class CyclicBarrierExample2 {
	static Logger logger = Logger.getLogger(CyclicBarrierExample2.class.getName());
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executorService.execute(()->{
                try{
                    race(threadNum);
                }catch (Exception e){
                    logger.error("exception : {}",e);
                }
            });
        }
        executorService.shutdown();
    }

    private static void race(int threadNum) throws Exception{
        Thread.sleep(1000);
        logger.info("{} is ready"+threadNum);
        try {
            //当执行await方法的线程达到指定的数目的时候就可以执行下面的方法了
            cyclicBarrier.await(2000, TimeUnit.MILLISECONDS);
        }catch (BrokenBarrierException | TimeoutException e){
            logger.error("{}",e);
        }
        logger.info("{} continue"+threadNum);
    }

}
