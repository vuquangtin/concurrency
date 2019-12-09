package threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * @author panghu
 * @Title: ThreadPoolExamle
 * @ProjectName example
 * @Description: newFixedThreadPool
 * 创建固定大小的线程池。线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程
 * @date 19-2-28 下午4:12
 */
@Slf4j
public class FixedThreadPoolExample {
	static Logger logger = Logger.getLogger(FixedThreadPoolExample.class.getName());
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
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
