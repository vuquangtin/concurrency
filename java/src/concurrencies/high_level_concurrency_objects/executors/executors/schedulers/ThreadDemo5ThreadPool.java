package executors.schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadDemo5ThreadPool {

	public static void main(String[] args) {
		/*-
		//1.创建一个单线程的线程池。这个线程只有一个线程在工作，也就是相当于单线程串行执行所有任务。
		//如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。此线程保证所有的任务的执行
		//顺序按照任务的提交顺序执行。
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(new MyRunnable6());
		es.execute(new MyRunnable6());
		es.shutdown();
		 */

		/*-
		//2.创建固定大小的线程池 。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。
		//线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补
		//充一个新的线程。
		ExecutorService es = Executors.newFixedThreadPool(2);
		es.execute(new MyRunnable6());
		es.execute(new MyRunnable6());
		es.execute(new MyRunnable6());
		es.shutdown();
		 */

		/*-
		//3.创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲
		//（60秒不执行任务）的线程，当任务数增加时，此线程又可以智能的添加新线程来处理任务。此线程
		//不会对线程池大小做限制，其大小完全依赖与操作系统（或者JVM）能够创建的最大线程大小。
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(new MyRunnable6());
		es.execute(new MyRunnable6());
		es.execute(new MyRunnable6());
		es.shutdown();
		 */

		// 4.创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。
		ScheduledExecutorService newScheduledThreadPool = Executors
				.newScheduledThreadPool(2);
		newScheduledThreadPool.schedule(new MyRunnable6(), 3000,
				TimeUnit.MILLISECONDS);
		newScheduledThreadPool.schedule(new MyRunnable6(), 4000,
				TimeUnit.MILLISECONDS);
		newScheduledThreadPool.schedule(new MyRunnable6(), 8000,
				TimeUnit.MILLISECONDS);
		newScheduledThreadPool.shutdown();

	}

}

class MyRunnable6 implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(Thread.currentThread().getName() + " -> " + i);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
