package synchronizers.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
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
public class SemaphoreExample3 {
	static Logger logger = Logger.getLogger(SemaphoreExample.class.getName());
    private static int threadCount = 20;

    public static void main(String[] args) throws Exception{

        ExecutorService executorService = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);


        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(()->{
                try{
                    //尝试获取一个许可，获取到就可以执行线程
                    if (semaphore.tryAcquire(5000, TimeUnit.MILLISECONDS)){
                        test(threadNum);
                        //释放多个许可
                        semaphore.release();
                    }
                }catch (Exception e){
                    logger.error("Exception:{}",e);
                }
            });
        }
        //await()保证线程执行完
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        logger.info("{}"+threadNum);
        Thread.sleep(1000);
    }

}
