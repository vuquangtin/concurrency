package synchronizers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

/**
 * @author panghu
 * @Title: FutureExample
 * @ProjectName example
 * @Description: TODO
 * @date 19-2-27 上午8:59
 */

/**
 * 这里有时候会先出现in main，有时候会先出现in callable这都是正常的，线程竞争问题而已
 */
@Slf4j
public class FutureExample {
	static Logger logger = Logger.getLogger(FutureExample.class.getName());
    static class MyCallable implements Callable<String>{

        @Override
        public String call() throws Exception {
            logger.info("do something in callable");
            Thread.sleep(5000);
            return "Done";
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new MyCallable());
        logger.info("do something in main");
        Thread.sleep(1000);
        String result = future.get();
        logger.info("result : {}"+result);
    }

}
