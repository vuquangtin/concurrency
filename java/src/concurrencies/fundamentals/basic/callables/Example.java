package basic.callables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Example {

	public static void main(String[] args) {
		ex1();
		ex2();
		ex3();
		ex4();
	}

	public static void ex4() {
		// Create thread pool using Executor Framework
		ExecutorService executor = Executors.newFixedThreadPool(10);

		List<Future<Integer>> list = new ArrayList<Future<Integer>>();
		for (int i = 0; i < 10; i++) {
			// Create new Calculator object
			Calculator c = new Calculator(i, i + 1);

			list.add(executor.submit(c));
		}

		for (Future f : list) {
			try {
				System.out.println(f.get(3, TimeUnit.SECONDS));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		executor.shutdown();
	}

	public static void ex3() {
		// Create thread pool using Executor Framework
		ExecutorService executor = Executors.newFixedThreadPool(10);

		List<Future<Integer>> list = new ArrayList<Future<Integer>>();
		for (int i = 0; i < 10; i++) {
			// Create new Calculator object
			Calculator c = new Calculator(i, i + 1);

			list.add(executor.submit(c));
		}

		for (Future f : list) {
			try {
				System.out.println(f.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		executor.shutdown();
	}

	public static void ex2() {
		// Create thread pool using Executor Framework
		ExecutorService executor = Executors.newFixedThreadPool(10);

		for (int i = 0; i < 10; i++) {
			// Create new Calculator object
			Calculator c = new Calculator(i, i + 1);

			Future f = executor.submit(c);
		}

		executor.shutdown();
	}

	public static void ex1() {
		// Create thread pool using Executor Framework
		ExecutorService executor = Executors.newFixedThreadPool(10);
		int total = 0;
		for (int i = 0; i < 10; i++) {
			// Create new Calculator object
			Calculator c = new Calculator(i, i + 1);

			try {
				total += executor.submit(c).get().intValue();
				System.out.println("total---:" + total);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("total:" + total);
		executor.shutdown();
	}
}