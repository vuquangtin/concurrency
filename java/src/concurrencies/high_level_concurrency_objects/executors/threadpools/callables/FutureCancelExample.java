package threadpools.callables;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class FutureCancelExample {
	 
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
 
        long startTime = System.currentTimeMillis();
        Future<Integer> future = executorService.submit(new CallableWorker(1));
 
        while (!future.isDone()) {
            System.out.println("Task is still working ...");
            Thread.sleep(200);
 
            long workingTime = (System.currentTimeMillis() - startTime);
            if (TimeUnit.SECONDS.toSeconds(workingTime) > 1000) {
                future.cancel(true);
            }
        }
 
        executorService.shutdown();
 
        if (!future.isCancelled()) {
            System.out.println("Task completed! Retrieving the result");
            System.out.println("Result = " + future.get());
        } else {
            System.out.println("Task was cancelled");
        }
 
        System.out.println("It will throw exception form here");
        System.out.println("Result = " + future.get());
    }
 
}