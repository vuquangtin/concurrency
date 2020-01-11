package threadpools.mycustom;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is a test class which runs simple for loop using CompletionService and
 * MyThreadPoolExecutor
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class TestClass {

	public static void main(String[] args) throws InterruptedException {
		CompletionService<Integer> threadService;
		ExecutorService service = Executors.newFixedThreadPool(2);
		threadService = new ExecutorCompletionService<Integer>(service);

		long b = System.currentTimeMillis();
		for (int i = 0; i < 50000; i++) {
			threadService.submit(new MyRunable(i));
		}

		service.shutdown();
		System.out.println("time taken by Completion Service " + (System.currentTimeMillis() - b));

		ResultListener<Integer> result = new DefaultResultListener<Integer>();
		MyThreadPool<Integer> newPool = new MyThreadPool<Integer>(2, result);
		long a = System.currentTimeMillis();

		int cc = 0;
		for (int i = 0; i < 50000; i++) {
			cc = cc + i;
		}
		System.out.println("time taken without any pool " + (System.currentTimeMillis() - a));
		a = System.currentTimeMillis();

		for (int i = 0; i < 50000; i++) {
			newPool.submit(new MyRunable(i));
		}

		newPool.stop();
		System.out.println("time taken by myThreadPool " + (System.currentTimeMillis() - a));
	}

}

class MyRunable implements Callable<Integer>

{
	int index = -1;

	public MyRunable(int index) {
		this.index = index;
	}

	@Override
	public Integer call() throws Exception {
		return index;
	}

}