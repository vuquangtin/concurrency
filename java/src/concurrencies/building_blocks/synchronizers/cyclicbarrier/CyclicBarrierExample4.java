package synchronizers.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

@Slf4j
public class CyclicBarrierExample4 {
	static Logger logger = Logger.getLogger(CyclicBarrierExample4.class.getName());
    /**
     *  同步等待的线程数,
     */
    //private static CyclicBarrier barrier = new CyclicBarrier(5);

    /**
     *  当等待的线程数到达要求时, 优先执行 runnable 接口中的代码.
     */
    private static CyclicBarrier barrier = new CyclicBarrier(5,()->{
        logger.info("call back is running");
    });

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++){
            final int threadNum = i;
            Thread.sleep(1000);
            exec.execute(()->{
                try {
                    race(threadNum);
                }catch (Exception e){
                    logger.error("exception", e);
                }
            });
        }

        exec.shutdown();
    }

    private static void race(int threadNum) throws Exception {
        Thread.sleep(1000);
        logger.info("{} is ready"+threadNum);
        /**
         * 此时表示一个线程准备完成, 进入等待, 当它达到指定的数字就可以开始执行了
         */
        //barrier.await();

        /**
         * 也可以指定等待时间, 如果超过了等待时间, 就开始执行
         */
        try {
            barrier.await(2000, TimeUnit.MILLISECONDS);
        } catch (BrokenBarrierException e){
            logger.warn("BrokenBarrierException", e);
        }

        logger.info("{} is continue"+ threadNum);
    }
}
