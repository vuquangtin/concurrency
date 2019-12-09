package executors.examples;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		// Submit Task and get placeholder for return value
		Future<Integer> future = executorService.submit(new Task());
		Integer number = future.get(); // blocking current thread until Task done!!!
		System.out.println("Result from the task is :" + number);
	}

}

class Task implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		return new Random().nextInt();
	}

}