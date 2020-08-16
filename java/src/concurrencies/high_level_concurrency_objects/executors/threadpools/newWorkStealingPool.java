package threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class newWorkStealingPool {
	public static void main(String... args) {
		System.out.println(Runtime.getRuntime().availableProcessors());
		ExecutorService newWorkStealingPool = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());
	}
}