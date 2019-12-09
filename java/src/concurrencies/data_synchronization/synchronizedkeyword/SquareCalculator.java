package synchronizedkeyword;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class SquareCalculator {

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public Future<Integer> calculate(Integer input) {
		return executor.submit(() -> {
			Thread.sleep(1000);
			return input * input;
		});
	}

	public static void main(String[] args) {

		Future<Integer> future = new SquareCalculator().calculate(10);

		while (!future.isDone()) {
			System.out.println("Calculating...");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			Integer result = future.get();
			result = future.get(500, TimeUnit.MILLISECONDS);
			System.out.println(result);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Future<Integer> future4 = new SquareCalculator().calculate(4);
		System.out.println(future4);
		boolean canceled = future.cancel(true);
		SquareCalculator squareCalculator = new SquareCalculator();
		System.out.println(canceled);
		
		Future<Integer> future1 = squareCalculator.calculate(10);
		Future<Integer> future2 = squareCalculator.calculate(100);

		while (!(future1.isDone() && future2.isDone())) {
			System.out.println(String.format("future1 is %s and future2 is %s",
					future1.isDone() ? "done" : "not done",
					future2.isDone() ? "done" : "not done"));
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Integer result1;
		try {
			result1 = future1.get();
			Integer result2 = future2.get();
			System.out.println(result1 + " and " + result2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		squareCalculator.shutdown();

	}

	private void shutdown() {
		// TODO Auto-generated method stub
		
	}
}
