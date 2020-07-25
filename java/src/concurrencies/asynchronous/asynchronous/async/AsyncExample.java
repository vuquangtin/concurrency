package asynchronous.async;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import asynchronous.async.model.User;

/**
 * Run a task asynchronously and return the result using supplyAsync() -
 * 
 * CompletableFuture.runAsync() is useful for tasks that don’t return anything.
 * But what if you want to return some result from your background task?
 * 
 * Well, CompletableFuture.supplyAsync() is your companion. It takes a Supplier
 * <T> and returns CompletableFuture<T> where T is the type of the value
 * obtained by calling the given supplier -
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class AsyncExample {

	public static void main(String[] args) {

		ExecutorService executor1 = Executors.newSingleThreadExecutor();
		ExecutorService executor2 = Executors.newSingleThreadExecutor();

		Supplier<List<Long>> supplyIDs = () -> {
			sleep(200);
			return Arrays.asList(1L, 2L, 3L);
		};

		Function<List<Long>, CompletableFuture<List<User>>> fetchUsers = ids -> {
			sleep(300);
			System.out.println("Function is currently running in " + Thread.currentThread().getName());
			Supplier<List<User>> userSupplier = () -> {
				System.out.println("Currently running in " + Thread.currentThread().getName());
				return ids.stream().map(User::new).collect(Collectors.toList());
			};
			return CompletableFuture.supplyAsync(userSupplier, executor2);
		};

		Consumer<List<User>> displayer = users -> {
			System.out.println("Running in " + Thread.currentThread().getName());
			users.forEach(System.out::println);
		};

		CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIDs);
		completableFuture.thenComposeAsync(fetchUsers, executor2).thenAcceptAsync(displayer, executor1);

		sleep(1_000);
		executor1.shutdown();
		executor2.shutdown();
	}

	private static void sleep(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
		}
	}
}