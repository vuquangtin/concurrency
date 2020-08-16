package basic.runnabletocallable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 * @see https://knpcode.com/java/concurrency/how-to-convert-runnable-into-
 *      callable-java/
 */
public class RunnableToCallable {

	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(2);
		// converting runnable task to callable
		Callable<Object> callable = Executors.callable(new MyRunnable());
		// submit method
		Future<Object> f = es.submit(callable);
		try {
			System.out.println("Result- " + f.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		es.shutdown();
	}

}

class MyRunnable implements Runnable {
	@Override
	public void run() {
		System.out.println("Running task..");
	}
}
