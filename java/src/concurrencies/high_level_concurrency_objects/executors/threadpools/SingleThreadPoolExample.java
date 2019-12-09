package threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * @author panghu
 * @Title: CachedThreadPoolExample
 * @ProjectName example
 * @Description:
 * 创建一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。
 * 如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。
 * 此线程池保证所有任务的执行顺序按照任务的提交顺序执行
 * @date 19-2-28 下午4:12
 */
@Slf4j
public class SingleThreadPoolExample {
	static Logger logger = Logger.getLogger(SingleThreadPoolExample.class.getName());
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("task : {}"+index);
                }
            });
        }
        executorService.shutdown();
    }

}
