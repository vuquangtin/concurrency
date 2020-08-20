package concurrency.java.wrappers.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Run multiple Callable parallel
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ConcurrentParallelTest {

	public static void main(String args[]) throws Exception {
		// get tasks
		final List<Callable<String>> tasks = getAllTasks();
		// run tasks
		List<CallableWrapper<String>> executedTasks = new CallableParallelExecutor<String>()
				.execute(tasks);
		// show results / errors
		for (CallableWrapper<String> t : executedTasks) {
			// extra id, not really useful since they are in order
			System.out.println("id: " + t.getId());
			System.out.println("\tresult: " + t.getResult());
			System.out.println("\terror: " + t.getError());
		}
	}

	private static List<Callable<String>> getAllTasks() {
		List<Callable<String>> listTasks = new ArrayList<Callable<String>>();
		for (int i = 1; i < 10; i++) {
			String ls_name = "Task_" + i;
			final int cnt = i;

			/*
			 * Use callable since Runnable cannot return a result, and I do not
			 * want to increase the complexity that handle result mapping in the
			 * run method
			 */
			listTasks.add(new Callable<String>() {

				@Override
				public String call() throws Exception {
					System.out.println("### Thread " + ls_name + " start");
					if (cnt == 7)
						throw new Exception("Some error occured");
					Thread.sleep(3000);
					System.out.println("### Thread " + ls_name + " end");
					return "result from " + ls_name;
				}
			});
		}
		return listTasks;
	}
}
