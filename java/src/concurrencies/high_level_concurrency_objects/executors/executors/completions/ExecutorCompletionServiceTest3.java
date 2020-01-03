package executors.completions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class ExecutorCompletionServiceTest3 {

	final static ExecutorService executor = Executors.newCachedThreadPool();

	public static void main(String[] args) {
		Callable<Integer> c0 = () -> {
			System.out.println("I am nothing!");
			return null;
		};
		Callable<Integer> c1 = () -> {
			Thread.sleep(1000);
			Integer n = new Random().nextInt(10);
			System.out.println("I am c1 and number is " + n);
			return n;
		};
		Callable<Integer> c2 = () -> {
			Integer n = new Random().nextInt(10);
			System.out.println("I am c2 and number is " + n);
			return n;
		};

		ArrayList<Callable<Integer>> callables = new ArrayList<>(3);
		callables.add(c0);
		callables.add(c1);
		callables.add(c2);

		CompletionService<Integer> ecs = new ExecutorCompletionService<>(
				executor);

		// Example one: get result set

		// recommend
		// LinkedList<Future<Integer>> futures = new LinkedList<>();
		// for (Callable<Integer> c : callables) {
		// Future<Integer> future = ecs.submit(c);
		// futures.add(future);
		// }

		// it's not bad
		LinkedList<Future<Integer>> futures = new LinkedList<>();
		for (Callable<Integer> c : callables) {
			Future<Integer> future = ecs.submit(c);
			futures.add(future);
		}

		// Example two: get result set
		int size = callables.size();
		try {
			for (int i = 0; i < size; i++) {
				Future<Integer> future = ecs.take();
				Integer n = future.get();
				System.out.println("result is " + n);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			System.out.println("shutdown executor");
		}

		try {
			for (Future<Integer> f : futures) {
				Integer n = f.get();
				System.out.println("futures ...... result is " + n);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}

/**
 * interface CompletionService<V> 这个接口是为了解耦 新异步任务的计算过程 与 已经完成任务结果的处理过程 Memory
 * consistency effects(存储器一致性效果): 先提交的任务,先执行,任务成功完成的顺序对应着执行take方法去除结果的顺序.
 *
 * class ExecutorCompletionService<V> implements CompletionService<V>
 * 轻量级,适合处理一组任务. 内置一个 Executor
 * executor,通常是一个线程池.使用该线程池任务,但它并不会管理线程池,包括没有关闭线程池的方法,需要单独维护线程池. 内置一个
 * BlockingQueue<Future<V>> completionQueue,用于存放任务的结果. 先执行完成的任务,其结果优先存放到
 * completionQueue 中. ExecutorCompletionService.take 方法是执行
 * BlockingQueue.take,会阻塞线程(慎用).
 */
