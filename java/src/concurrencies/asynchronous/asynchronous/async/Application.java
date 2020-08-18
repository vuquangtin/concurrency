package asynchronous.async;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class Application {
	public static void main(String[] args) {
		demoAnyOf();
		//demoAllOf();
		//demoAcceptEither();
		//demoThenAcceptBoth();
		//demoApplyToEither();
		//demoStart();
		//demoCompose();
		//demoDifferentTypes();
		// demoWait();
		// demoNewCompletableFuture();
		// demoThenApply();
		// demoAsync();
		// demoSync();
		// demoExceptions();
		// demoCombine();
	}

	static void demoStart() {
		CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> { 
			logThread("supplyAsync");
			return 42;
		});
		sleep(1000);
	}
	static void demoCompose() {
		header("demoCompose");
		// thenCompose() is used to chain one future dependent on the other
		CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> { 
			logThread("supplyAsync 1");
			sleep(5000);
			return 42;
		});
		CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> { 
			logThread("supplyAsync 2");
			sleep(800);
			return 77;
		});
		CompletableFuture<Integer> f3 = CompletableFuture.supplyAsync(() -> { 
			logThread("supplyAsync 3");
			sleep(2000);
			return -1;
		}).thenCompose(x -> x > 0 ? f1 : f2);
		get(f3);
	}
	static void demoWait() {
		final CompletableFuture<Integer> f = new CompletableFuture<>();
		class MyThread extends Thread {
			@Override
			public void run() {
				try {
					System.out.println("waiting");
					int value = f.get();
					System.out.println("result = " + value);
				}
				catch (ExecutionException | InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
		;
		for (int i = 0; i < 5; i++)
			new MyThread().start();
		sleep(1000);
		System.out.println(f.isDone());
		f.complete(42);
		System.out.println(f.isDone());
		sleep(1000);
	}

	static void demoDifferentTypes() {
		header("demoDifferentTypes");
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "10");
		CompletableFuture<Integer> f2 = f1.thenApply(Integer::parseInt);
		CompletableFuture<Double> f3 = f2.thenApply(r -> r * r * Math.PI);
		get(f3);
		CompletableFuture<Double> f4 = 
				CompletableFuture.supplyAsync(() -> "10")
				.thenApply(Integer::parseInt)
				.thenApply(r -> r * r * Math.PI);
		get(f4);
	}

	static void demo1() {
		CompletableFuture<Integer> f = new CompletableFuture<>();
		f.thenAccept(x -> System.out.println(x));
		System.out.println(f.isDone());
		f.complete(42);
		System.out.println(f.isDone());
	}

	static void demo2() {
		CompletableFuture<Integer> f = new CompletableFuture<>();
		f.thenApply(x -> x + x).thenAccept(x -> System.out.println(x));
		f.complete(42);
	}

	static void demo3() {
		CompletableFuture<Integer> f = new CompletableFuture<>();
		f.thenApply(x -> x + x).thenApply(x -> x + 1).thenAccept(x -> System.out.println(x));
		// f.thenApply(x -> x + x).thenApply(x -> x + 1).thenAccept(x ->
		// System.out.println(x)).thenAccept((x) -> System.out.println(x));
		f.complete(42);
	}

	static void demo10() {
		CompletableFuture<Integer> f = new CompletableFuture<>();
		f.thenApply(x -> x * x).thenAccept(x -> System.out.print(x)).thenRun(() -> System.out.println());
		f.complete(42);
	}

	static void demox() {
		Callable<Integer> callable = new Callable<Integer>() {
			public Integer call() throws Exception {
				sleep(1000);
				return 42;
			}
		};
		ExecutorService executor = Executors.newFixedThreadPool(5);
		// Future<Integer> lf = executor.submit(() -> { sleep(1000); return 42;
		// });
		Future<Integer> f = executor.submit(callable);
		try {
			System.out.println("waiting...");
			System.out.println("result = " + f.get());
		}
		catch (InterruptedException | ExecutionException e) {
			System.out.println(e);
		}
	}

	static void demoy() {
		Supplier<Integer> supplier = new Supplier<Integer>() {
			public Integer get() {
				sleep(1000);
				return 42;
			}
		};
		ExecutorService executor = Executors.newFixedThreadPool(5);
		CompletableFuture<Integer> f = CompletableFuture.supplyAsync(supplier, executor);
		// CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
		// sleep(1000); return 42; }, executor);
		try {
			System.out.println("waiting...");
			System.out.println("result = " + f.get());
		}
		catch (InterruptedException | ExecutionException e) {
			System.out.println(e);
		}
	}

	static void demoU() {
		ExecutorService executor = Executors.newFixedThreadPool(5);

		// static CompletableFuture<Void> runAsync(Runnable r, Executor
		// executor)
		CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> System.out.println("Hello"), executor);

		// static <T> CompletableFuture<T> supplyAsync(Supplier<T> s, Executor
		// executor)
		CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> 42, executor);

		try {
			System.out.println(f1.get()); // -> null
			System.out.println(f2.get()); // -> 42
		}
		catch (InterruptedException | ExecutionException e) {
			System.out.println(e);
		}

		executor.shutdown();
	}

	static void demoV() {
		ExecutorService executor = Executors.newFixedThreadPool(5);

		CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
			sleep(1000);
			return 42;
		}, executor);

		sleep(500);
		f.complete(77);
		try {
			System.out.println(f.get()); // -> 77 (42 will be ignored)
		}
		catch (InterruptedException | ExecutionException e) {
			System.out.println(e);
		}

		executor.shutdown();
	}

	static void demoW() {
		// default: ava.util.concurrent.ForkJoinPool.commonPool()
		CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> 42);
		try {
			System.out.println(f.get()); // -> 42
		}
		catch (InterruptedException | ExecutionException e) {
			System.out.println(e);
		}
	}

	static void demoNewCompletableFuture() {
		final CompletableFuture<Integer> f = new CompletableFuture<>();
		// Wert kann nur mittels complete gesetzt werden
		new Thread() {
			@Override
			public void run() {
				Application.sleep(1000);
				f.complete(42);
			}
		}.start();
		try {
			System.out.println(f.get()); // -> 42
		}
		catch (InterruptedException | ExecutionException e) {
			System.out.println(e);
		}
	}

	static void demoThenApply() {
		// interface CompletionStage<T> {
		// <U> CompletionStage<U> thenApplyAsync(Function<? super T, ? extends
		// U> func);
		// CompletionStage<Void> thenApplyAsync(Consumer<? super T> action);
		// ....
		// }

		CompletableFuture.supplyAsync(() -> 42).thenApplyAsync((i) -> i + 1).thenAcceptAsync((i) -> {
			System.out.println(i);
		});
		sleep(1000);
	}

	static void demoAsync() {
		Executor executor = new Executor() {
			@Override
			public void execute(Runnable r) {
				System.out.println("Thread starts");
				new Thread(r).start();
			}
		};
		// ExecutorService executor = Executors.newFixedThreadPool(5);
		logThread("main");
		CompletableFuture<Void> f = CompletableFuture.supplyAsync(() -> {
			logThread("supplyAsync");
			return 42;
		}, executor).thenApplyAsync((i) -> {
			logThread("thenSupplyAsync");
			return i + 1;
		}).thenAcceptAsync((i) -> {
			logThread("thenAcceptAsync");
			System.out.println(i);
		});
		get(f);
	}

	static void demoSync() {
		logThread("main");
		CompletableFuture<Void> f = CompletableFuture.supplyAsync(() -> {
			logThread("supplysync");
			return 42;
		}).thenApply((i) -> {
			logThread("thenSupplyAsync");
			return i + 1;
		}).thenAccept((i) -> {
			logThread("thenAcceptAsync");
			System.out.println(i);
		});
		get(f);
	}

	static void demoExceptions() {
		header("demoExceptions");
		CompletableFuture<Void> f = CompletableFuture.supplyAsync(() -> {
			return 42;
		}).thenApplyAsync((i) -> {
			logThread("thenApply");
			throw new RuntimeException("abc");
		}).exceptionally(e -> {
			logThread("exceptionally");
			return -1;
		}).thenAcceptAsync((i) -> {
			System.out.println(i);
		});
		get(f);
	}

	static void demoCombine() {
		header("demoCombine");
		CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
			sleep(1000);
			return 42;
		});
		CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
			sleep(500);
			return 77;
		});
		CompletableFuture<Integer> f = f1.thenCombineAsync(f2, (x, y) -> x + y);
		// f.thenAccept(x -> System.out.println(x));
		get(f);
	}
	
	// <U> CompletableFuture<Void> thenAcceptBoth(CompletableFuture<? extends U> other, BiConsumer<? super T,? super U> block)
	// CompletableFuture<Void> runAfterBoth(CompletableFuture<?> other, Runnable action)
	// They work similarly to thenAccept() and thenRun() but wait for two futures instead of one
	static void demoThenAcceptBoth() {
		header("demoThenAcceptBoth");
		CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
			sleep(1000);
			return 42;
		});
		CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
			sleep(500);
			return 77;
		});
		CompletableFuture<Void> f = f1.thenAcceptBoth(f2, (x, y) -> {
			System.out.println(x + " " + y);
		});
		//System.out.println(get(f1) + " " + get(f2));  // blocking!!!
		get(f);
	}
	
	static void demoAcceptEither() {
		header("demoAcceptEither");
		CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
			sleep(2000);
			return 42;
		});
		CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
			sleep(500);
			return 42;
		});
		CompletableFuture<Void> f = f1.acceptEither(f2, x -> {
			System.out.println(x);
		});
		get(f);
	}
	
	static void demoApplyToEither() {
		header("demoApplyToEither");
		CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
			sleep(2000);
			return 42;
		});
		CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
			sleep(500);
			return 42;
		});
		CompletableFuture<String> f = f1.applyToEither(f2, x -> "\"" + x + "\"");
		get(f);
	}

	static void demoAllOf() {
		// static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs)
		header("demoAllOf");
		List<String> results = new ArrayList<>();
		
		CompletableFuture<Void> f1 = CompletableFuture.supplyAsync(() -> {
			logThread("f1");
			sleep(500);
			results.add("Hello");
			return null;
		});
		
		CompletableFuture<Void> f2 = CompletableFuture.supplyAsync(() -> {
			logThread("f2");
			sleep(1500);
			return "World";
		}).thenAccept(s -> results.add(s));

		CompletableFuture<Void> f = CompletableFuture.allOf(f1, f2);
		get(f);
		// CompletableFuture.allOf(f1, f2).get();
		results.forEach(s -> System.out.println(s));
	}

	static void demoAnyOf() {
		// static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs)		
		header("demoAnyOf");
		
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
			logThread("f1");
			sleep(500);
			return "Hello";
		});
		
		CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
			logThread("f2");
			sleep(1500);
			return "World";
		});

		CompletableFuture<Object> f = CompletableFuture.anyOf(f1, f2);
		get(f);
	}

	static void get(CompletableFuture<?> f) {
		try {
			System.out.println(">> waiting");
			final long t0 = System.nanoTime();
			final Object result = f.get();
			final long t1 = System.nanoTime();
			final long duration = (t1 - t0) / 1000_000;
			System.out.println("<< result = " + result + " [" + duration + " ms]");
		}
		catch (ExecutionException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	static void logThread(String location) {
		System.out.println(location + " Thread: " + Thread.currentThread().getId());
	}

	static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	static void header(String text) {
		final String LINE = "+-----------------------------------------";
		System.out.println(LINE);
		System.out.println("| " + text);
		System.out.println(LINE);
	}
}