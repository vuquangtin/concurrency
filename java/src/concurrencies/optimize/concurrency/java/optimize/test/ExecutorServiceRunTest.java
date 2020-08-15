package concurrency.java.optimize.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import concurrency.java.optimize.MyRunnable;
import learn.threads.vn.RunnableTest;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExecutorServiceRunTest {

	/**
	 * @author JUC void
	 * 
	 *         1初步体验： 先将5个任务分配给5个线程去执行， 剩余五个存入队列当中， 剩下五个被抛弃
	 * 
	 *         2执行顺序 [核心5 最大8 队列5]
	 * 
	 *         首先核心线程数 12345 然后队列填满 678910 然后紧接着执行 11 12 13 (14 15被抛弃) 最终顺序：
	 *         12345 11 12 13 678910 （14,15抛弃）
	 */
	public static BlockingQueue<Runnable> blockQueue = new ArrayBlockingQueue<Runnable>(
			10, true);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * ExecutorService executorService = new ThreadPoolExecutor(5, 10, 1,
		 * TimeUnit.MINUTES, blockQueue);
		 */
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 8, 200,
				TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5)) {
			@Override
			protected void beforeExecute(Thread t, Runnable r) {
				// TODO Auto-generated method stub
				super.beforeExecute(t, r);

			}

			@Override
			protected void afterExecute(Runnable r, Throwable t) {
				// TODO Auto-generated method stub
				super.afterExecute(r, t);
			}
		};

		for (int i = 0; i < 15; i++) {
			MyRunnable myTask = new MyRunnable(i);
			try {

				if (executor.getQueue().size() == 5) {// 线程池满了
					System.out.println("已经抛弃-------=======" + i);
					System.out.println("此时的阻塞队列"
							+ executor.getQueue().toString());
				}
				/*
				 * if(executor.getQueue().size()!=5){//线程池满了
				 * System.out.println("此时的阻塞队列"+executor.getQueue().toString());
				 * Thread.currentThread().notify(); }
				 */
				executor.execute(myTask);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("此时被抛弃的数据：" + i);
			}

			System.out.println("线程池中线程数目：" + executor.getPoolSize());
			System.out.println("队列中等待执行的任务数目：" + executor.getQueue().size());
			System.out.println("此时的阻塞队列" + executor.getQueue().toString());

			System.out.println("--------------");
			/*
			 * if(executor.getQueue().size()==5){//线程池满了
			 * System.out.println("此时的阻塞队列"+executor.getQueue().toString()); }
			 */

		}
		executor.shutdown();
	}

}
