package concurrency.java.wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import concurrency.java.optimize.tasks.DemoOneTask;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExecutorServiceWrapperTest {

	public static void main(String[] args) {

		System.out.println();
		// surse utile
		// https://www.baeldung.com/java-executor-service-tutorial
		// https://www.baeldung.com/java-mutex
		ExecutorServiceWrapper executor = new ExecutorServiceWrapper(10);
		List<Future> list = new ArrayList<>();
		IntStream.range(1, 15).forEach(
				(i) -> list.add(executor.submit(new DemoOneTask())));
		list.forEach(f -> {
			try {
				System.out.println(f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});
		executor.shutdown();
	}
}