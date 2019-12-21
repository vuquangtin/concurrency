package cookbook.chapter4.threadexecutors.recipe10_2;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletionServiceTest {

	public static void main(String[] args) {
		ExecutorService taskExecutor = Executors.newFixedThreadPool(3);
		CompletionService<CalcResult> taskCompletionService = new ExecutorCompletionService<CalcResult>(
				taskExecutor);

		int submittedTasks = 5;
		for (int i = 0; i < submittedTasks; i++) {
			taskCompletionService.submit(new CallableTask(String.valueOf(i),
					(i * 10), ((i * 10) + 10)));
			System.out.println("Task " + String.valueOf(i) + "subitted");
		}
		for (int tasksHandled = 0; tasksHandled < submittedTasks; tasksHandled++) {
			try {
				System.out.println("trying to take from Completion service");
				Future<CalcResult> result = taskCompletionService.take();
				System.out
						.println("result for a task availble in queue.Trying to get()");
				// above call blocks till atleast one task is completed and
				// results availble for it
				// but we dont have to worry which one

				// process the result here by doing result.get()
				CalcResult l = result.get();
				System.out.println("Task " + String.valueOf(tasksHandled)
						+ "Completed - results obtained : "
						+ String.valueOf(l.result));

			} catch (InterruptedException e) {
				// Something went wrong with a task submitted
				System.out.println("Error Interrupted exception");
				e.printStackTrace();
			} catch (ExecutionException e) {
				// Something went wrong with the result
				e.printStackTrace();
				System.out.println("Error get() threw exception");
			}
		}
		taskExecutor.shutdown();
	}
}

class CalcResult {
	long result;

	CalcResult(long l) {
		result = l;
	}
}

class CallableTask implements Callable<CalcResult> {
	String taskName;
	long input1;
	int input2;

	public CallableTask(String name, long v1, int v2) {
		taskName = name;
		input1 = v1;
		input2 = v2;
	}

	public CalcResult call() throws Exception {
		System.out.println(" Task " + taskName + " Started -----");
		for (int i = 0; i < input2; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				System.out.println(" Task " + taskName + " Interrupted !! ");
				e.printStackTrace();
			}
			input1 += i;
		}
		System.out.println(" Task " + taskName + " Completed @@@@@@");
		return new CalcResult(input1);
	}

}