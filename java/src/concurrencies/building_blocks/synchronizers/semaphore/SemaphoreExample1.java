package synchronizers.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

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
public class SemaphoreExample1 {
	static Logger logger = Logger.getLogger(SemaphoreExample.class.getName());
    private static int threadCount = 20;

    public static void main(String[] args) throws Exception{

        ExecutorService executorService = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);


        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(()->{
                try{
                    //获得一个许可
                    semaphore.acquire();
                    test(threadNum);
                    //释放一个许可
                    semaphore.release();
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
