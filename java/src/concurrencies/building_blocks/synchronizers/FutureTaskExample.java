package synchronizers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * @author panghu
 * @Title: FutureTaskExample
 * @ProjectName example
 * @Description: 线程池的创建和使用
 * @date 19-2-27 上午10:03
 */
@Slf4j
public class FutureTaskExample {
	static Logger logger = Logger.getLogger(FutureTaskExample.class.getName());
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("do something in callable");
                Thread.sleep(5000);
                return "Done";
            }
        });
        new Thread(futureTask).start();
        logger.info("do something in main");
        Thread.sleep(1000);
        String result = futureTask.get();
        logger.info("result : {}"+result);
    }

}
