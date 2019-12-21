package joinkeyword;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class JoinMain2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IntHolder aHolder = new IntHolder();
		aHolder.Number = 0;

		IncrementalRunable A = new IncrementalRunable(1, aHolder);
		IncrementalRunable B = new IncrementalRunable(2, aHolder);
		IncrementalRunable C = new IncrementalRunable(3, aHolder);

		ExecutorService exec = Executors.newFixedThreadPool(3);
		exec.execute(A);
		exec.execute(B);
		exec.execute(C);
		// Don't know what to do here
		exec.shutdown();
		try {
			while (!exec.awaitTermination(24L, TimeUnit.HOURS)) {
				System.out.println("Not yet. Still waiting for termination");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Use shutdown() + awaitTermination() combination.
		System.out.println("All threads completed...");

		// NOTE: invokeAll() will not return until all the tasks are completed
		// (either by failing or completing successful execution).
		List<Callable<Object>> calls = new ArrayList<Callable<Object>>();
		calls.add(Executors.callable(new IncrementalRunable(1, aHolder)));
		calls.add(Executors.callable(new IncrementalRunable(2, aHolder)));
		calls.add(Executors.callable(new IncrementalRunable(3, aHolder)));
		ExecutorService executor = Executors.newFixedThreadPool(3);
		try {
			List<Future<Object>> futures = executor.invokeAll(calls);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All threads completed...");

		ExecutorService exec2 = Executors.newFixedThreadPool(3);
		Collection<Future<?>> tasks = new LinkedList<Future<?>>();

		Future<?> future = exec2.submit(A);
		tasks.add(future);
		future = exec2.submit(B);
		tasks.add(future);
		future = exec2.submit(C);
		tasks.add(future);

		// wait for tasks completion
		for (Future<?> currTask : tasks) {
			try {
				currTask.get();
			} catch (Throwable thrown) {
				System.out.println("Error while waiting for thread completion");
			}
		}
		System.out.println("All threads completed...");
	}
}