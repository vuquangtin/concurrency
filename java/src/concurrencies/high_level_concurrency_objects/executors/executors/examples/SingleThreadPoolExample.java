package executors.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadPoolExample {

	public static void main(String[] args) {
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.submit(() -> System.out.println("Task"));
	}

}