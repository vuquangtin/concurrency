package concurrency.java.executors;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadPoolPerformance {

	public ImmutableList<ExecutorService> executorServices = ImmutableList.of(
			Executors.newFixedThreadPool(1), Executors.newFixedThreadPool(1),
			Executors.newFixedThreadPool(2), Executors.newFixedThreadPool(3),
			Executors.newFixedThreadPool(4), Executors.newFixedThreadPool(5),
			Executors.newFixedThreadPool(6), Executors.newFixedThreadPool(7),
			Executors.newFixedThreadPool(8), Executors.newFixedThreadPool(9),
			Executors.newFixedThreadPool(10), Executors.newFixedThreadPool(11),
			Executors.newFixedThreadPool(12), Executors.newFixedThreadPool(13),
			Executors.newFixedThreadPool(14), Executors.newFixedThreadPool(15),
			Executors.newFixedThreadPool(16), Executors.newFixedThreadPool(17),
			Executors.newFixedThreadPool(18), Executors.newFixedThreadPool(19),
			Executors.newFixedThreadPool(20), Executors.newFixedThreadPool(21),
			Executors.newFixedThreadPool(22), Executors.newFixedThreadPool(23),
			Executors.newFixedThreadPool(24), Executors.newFixedThreadPool(25),
			Executors.newFixedThreadPool(26), Executors.newFixedThreadPool(27),
			Executors.newFixedThreadPool(28), Executors.newFixedThreadPool(29),
			Executors.newFixedThreadPool(30));

	public static void main(String[] args) {

		ThreadPoolPerformance threadPoolPerformance = new ThreadPoolPerformance();
		threadPoolPerformance.exeImmutableService();

		System.out.println(Runtime.getRuntime().availableProcessors());

	}

	public void exeImmutableService() {

		for (Integer i = 1; i < executorServices.size(); i++) {

			List<Future<Integer>> futureList = Lists.newArrayList();
			long begin = System.currentTimeMillis();
			for (Integer j = 0; j < 100; j++) {
				futureList.add(executorServices.get(i).submit(
						new ExecutorThread(j)));
			}

			for (Future<Integer> future : futureList) {
				try {
					Integer result = future.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}

			}
			long end = System.currentTimeMillis();
			System.out.println(i + ":" + (end - begin));
			executorServices.get(i).shutdown();
		}

	}

	static class ExecutorThread implements Callable<Integer> {

		private Integer status = 0;

		ExecutorThread(Integer status) {
			this.status = status;
		}

		private void occupationCpu() {
			for (Integer i = 1; i < status; i++) {
				for (Integer j = 1; j < status * 10; j++) {
					for (Integer k = 1; k < status; k++) {
						double tmp = (i * j) * 1.1 / k;
					}
					// double tmp = (9.9*i*j)/(i*i*1.1);
				}
			}
		}

		public Integer call() throws Exception {
			occupationCpu();
			return status;
		}
	}
}