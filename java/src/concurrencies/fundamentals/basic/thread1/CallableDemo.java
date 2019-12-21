package basic.thread1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class CallableDemo {
	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		new CallableDemo();
	}

	public CallableDemo() throws InterruptedException, ExecutionException {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Running Shutdown Hook");
			}
		});
		ExecutorService service = Executors.newSingleThreadExecutor();
		SumTask sumTask = new SumTask(20);
		Future<Integer> future = service.submit(sumTask);
		Integer result = future.get();
		System.out.println(result);

		Future<Integer> future2 = service.submit(() -> handleSyncProfile(1000));
		Consumer<String> c = s -> System.out.println(s);
		Consumer<Integer> handle = v -> handleSyncProfileStatic(v);
		c.accept("sdfjsdkfjsdk");
		handle.accept(1000);
		Future<Integer> future4 = service
				.submit(() -> handleSyncProfileStatic(70));
		Future<Integer> future3 = service.submit(new CallableDemoInner());
		service.shutdown();
		System.out.println(future2.get());
		System.out.println(future3.get());
		List<Integer> list = Arrays.asList(12, 5, 45, 18, 33, 24, 40);

		// Using an anonymous class
		Numbers.findNumbers(list, new BiPredicate<Integer, Integer>() {
			public boolean test(Integer i1, Integer i2) {
				return Numbers.isMoreThanFifty(i1, i2);
			}
		});

		// Using a lambda expression
		Numbers.findNumbers(list, (i1, i2) -> Numbers.isMoreThanFifty(i1, i2));

		// Using a method reference
		Numbers.findNumbers(list, Numbers::isMoreThanFifty);
	
	}

	private class CallableDemoInner implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			// TODO Auto-generated method stub
			return handleSyncProfile(500);
		}
	}

	public static int handleSyncProfileStatic(int total) {
		int totalMax = 0;
		while (true) {
			for (int i = 0; i < total; i++) {
				System.out.println(i);
				totalMax += i;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
		return totalMax;
	}

	private int handleSyncProfile(int total) {
		int totalMax = 0;
		while (true) {
			for (int i = 0; i < total; i++) {
				System.out.println(i);
				totalMax += i;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
		return totalMax;
	}
}

class SumTask implements Callable<Integer> {
	private int num = 0;

	public SumTask(int num) {
		this.num = num;
	}

	@Override
	public Integer call() throws Exception {
		int result = 0;
		for (int i = 1; i <= num; i++) {
			result += i;
		}
		return result;
	}
}

class Numbers {
	public static boolean isMoreThanFifty(int n1, int n2) {
		return (n1 + n2) > 50;
	}

	public static List<Integer> findNumbers(List<Integer> l,
			BiPredicate<Integer, Integer> p) {
		List<Integer> newList = new ArrayList<>();
		for (Integer i : l) {
			if (p.test(i, i + 10)) {
				newList.add(i);
			}
		}
		return newList;
	}
}