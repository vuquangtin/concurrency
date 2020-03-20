package running.threads.managers.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ConcurrencyWithCompletableFuture1 {

	public static void main(String[] args) {
		List<String> allPerson = new ArrayList<>();
		allPerson.add("A");
		allPerson.add("B");
		allPerson.add("C");
		allPerson.add("D");
		allPerson.add("E");

		ExecutorService executor = Executors.newFixedThreadPool(2);
		allPerson.forEach((_item) -> {
			Future<List<String>> submit = executor.submit(() -> {
				return work1(_item);
			});
			try {
				for (String data : submit.get()) {
					work2(data);
				}
			} catch (InterruptedException | ExecutionException ex) {
				Logger.getLogger(
						ConcurrencyWithCompletableFuture1.class.getName()).log(null, ex);
			}
		});
		executor.shutdown();
	}

	private static List<String> work1(String _item) {
		System.out.println("Person" + _item + " -> work1");
		return Arrays.asList(_item + "_item" + 1, _item + "_item" + 2);
	}

	private static void work2(String data) {
		System.out.println("Person" + data + " -> work2");
	}
}