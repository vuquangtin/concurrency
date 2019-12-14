package synchronizers.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

@Slf4j
public class SemaphoreExample4 {
	static Logger logger = Logger.getLogger(SemaphoreExample4.class.getName());
    private final static int threadCount = 200;

    public static void main(String[] args) {

        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(20);

        for (int i = 0; i < threadCount; i++){
            final int threadNum = i;

            exec.execute(()->{
                try {
                    /**
                     *  acquire() 和 release() 默认获取和释放的信号量是 1,但是也可以自己指定,
                     *  如果 依次 acquire() 和 release() 的资源数总信号量相同, 则相当于单线程执行
                     */
                    /*semaphore.acquire();
                    test(threadNum);
                    semaphore.release();*/

                    /**
                     *  如果在某一时刻, 访问过多, 则会对多余的请求进行抛弃, 此时可以调用 tryAcquire() 方法来进行处理
                     */
                    if (semaphore.tryAcquire()){
                        test(threadNum);
                        semaphore.release();
                    }

                }catch (Exception e){
                    logger.error("exception", e);
                }
            });
        }

        exec.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        logger.info("{}"+ threadNum);
        Thread.sleep(1000);
    }

}
