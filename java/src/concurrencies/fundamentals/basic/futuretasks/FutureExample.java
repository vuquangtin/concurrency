package basic.futuretasks;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;

@Slf4j
public class FutureExample {
	static Logger logger = Logger.getLogger(FutureExample.class.getName());
    static class CallableTest implements Callable<String>{

        @Override
        public String call() throws Exception {
            logger.info("do something in callable");
            Thread.sleep(3000);
            return "Done";
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService exec = Executors.newCachedThreadPool();
        Future<String> future = exec.submit(new CallableTest());

        logger.info("do something in main");
        Thread.sleep(1000);
        String result = future.get();
        logger.info("{}"+ result);
    }

}
