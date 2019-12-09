package threadpools.callables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class InvokeAllExample {
	 
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Get ExecutorService from Executors utility class, thread pool size is 5
        ExecutorService executor = Executors.newFixedThreadPool(5);
 
        List<Callable<Integer>> callables = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            callables.add(new CallableWorker(i));
        }
 
        List<Future<Integer>> futures = executor.invokeAll(callables);
 
        int sum = 0;
        for (Future<Integer> future : futures) {
            sum += future.get();
        }
        System.out.println("Sum all = " + sum);
 
        executor.shutdown();
        System.out.println("Finished all threads ");
    }
}
