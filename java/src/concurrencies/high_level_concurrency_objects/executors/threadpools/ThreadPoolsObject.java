package threadpools;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class ThreadPoolsObject {
	public static void main(String[] args) {
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(() -> System.out.println("Hello World"));

		ExecutorService executorService = Executors.newFixedThreadPool(10);
		Future<String> future = executorService.submit(() -> "Hello World");
		// some operations
		try {
			String result = future.get();
			System.out.println(result);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void pool() {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(2);
		executor.submit(() -> {

			Thread.sleep(1000);
			return null;

		});

		executor.submit(() -> {
			Thread.sleep(1000);
			return null;
		});
		executor.submit(() -> {
			Thread.sleep(1000);
			return null;
		});

		assertEquals(2, executor.getPoolSize());
		assertEquals(1, executor.getQueue().size());

	}

	public void pool2() {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newCachedThreadPool();
		executor.submit(() -> {
			Thread.sleep(1000);
			return null;
		});
		executor.submit(() -> {
			Thread.sleep(1000);
			return null;
		});
		executor.submit(() -> {
			Thread.sleep(1000);
			return null;
		});

		assertEquals(3, executor.getPoolSize());
		assertEquals(0, executor.getQueue().size());
	}

	private void assertEquals(int i, int poolSize) {
		AtomicInteger counter = new AtomicInteger();

		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
			counter.set(1);
		});
		executor.submit(() -> {
			counter.compareAndSet(1, 2);
		});

	}

	public void poo() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
		executor.schedule(() -> {
			System.out.println("Hello World");
		}, 500, TimeUnit.MILLISECONDS);
	}

	public void countDownLatch() {
		CountDownLatch lock = new CountDownLatch(3);

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
		ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
			System.out.println("Hello World");
			lock.countDown();
		}, 500, 100, TimeUnit.MILLISECONDS);

		try {
			lock.await(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		future.cancel(true);
	}

	static class TreeNode {

		int value;

		Set<TreeNode> children;

		TreeNode(int value, TreeNode... children) {
			this.value = value;
			this.children = Sets.newHashSet(children);
		}
	}

	public static class CountingTask extends RecursiveTask<Integer> {

		private final TreeNode node;

		public CountingTask(TreeNode node) {
			this.node = node;
		}

		@Override
		protected Integer compute() {
			return node.value
					+ node.children
							.stream()
							.map(childNode -> new CountingTask(childNode)
									.fork())
							.collect(Collectors.summingInt(ForkJoinTask::join));
		}
	}

	public void treeNode() {
		TreeNode tree = new TreeNode(5, new TreeNode(3), new TreeNode(2,
				new TreeNode(2), new TreeNode(8)));

		ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
		int sum = forkJoinPool.invoke(new CountingTask(tree));
	}
}
