package concurrency.java.optimize.test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import concurrency.java.optimize.tasks.MyRunnable;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadPoolExecutorTest {

	public static void main(String[] args) throws InterruptedException {
		test1();
	}

	/**
	 * 验证规则1：如果线程数量 <= 核心线程数量, 那么直接启动一个核心线程来执行任务, 不会放入队列中
	 * 
	 * @throws InterruptedException
	 */
	public static void test1() throws InterruptedException {
		// 核心线程数为6个, 最大线程数为10个, 非核心线程超时时间为5s, 任务队列为SynchronousQueue
		ThreadPoolExecutor executor = new ThreadPoolExecutor(6, 10, 5,
				TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println("========== start three thread ==========");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println("========== start three thread again ==========");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(8000);
		System.out.println("----after 8 seconds----");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(10000);
		executor.shutdown();
	}

	/**
	 * 验证规则2:如果线程数量 > 核心线程数, 但 <= 最大线程数, 并且任务队列是LinkedBlockingDeque的时候,
	 * 超过核心线程数量的任务会放在任务队列中排队.
	 * 
	 * @throws InterruptedException
	 */
	public static void test2() throws InterruptedException {
		// 核心线程数为3个, 最大线程数为6个, 非核心线程超时时间为5s, 任务队列为LinkedBlockingDeque,
		// 因此最大线程数无效, 线程池中的线程数量不会超过3个
		ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 5,
				TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println("========== start three thread ==========");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println("========== start three thread again ==========");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(8000);
		System.out.println("----after 8 seconds----");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(8000);
		System.out.println("----after 8 seconds, start 11 thread ----");
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(10000);
		executor.shutdown();
	}

	/**
	 * 验证规则3: 如果线程数量 > 核心线程数, 但 <= 最大线程数, 并且任务队列是SynchronousQueue的时候,
	 * 线程池会创建新线程执行任务, 这些任务也不会被放在任务队列中. 这些线程属于非核心线程, 在任务完成后, 闲置时间达到了超时时间就会被清除.
	 * 
	 * @throws InterruptedException
	 */
	public static void test3() throws InterruptedException {
		// 核心线程数为3个, 最大线程数为6个, 非核心线程超时时间为5s, 任务队列为SynchronousQueue
		ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 5,
				TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println("========== start three thread ==========");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println("========== start three thread again ==========");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(8000);
		System.out.println("----after 8 seconds----");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(8000);
		System.out.println("----after 8 seconds, start 11 thread ----");
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(10000);
		executor.shutdown();
	}

	/**
	 * 验证规则4: 如果线程数量 > 核心线程数, 并且 > 最大线程数, 当任务队列是LinkedBlockingDeque,
	 * 会将超过核心线程的任务放在任务队列中排队. 也就是当任务队列是LinkedBlockingDeque并且没有大小限制时,
	 * 线程池的最大线程数设置是无效的, 他的线程数最多不会超过核心线程数.
	 * 
	 * @throws InterruptedException
	 */
	public static void test4() throws InterruptedException {
		// 核心线程数为3个, 最大线程数为4个, 非核心线程超时时间为5s, 任务队列为没有大小限制的LinkedBlockingDeque
		// ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5,
		// TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

		// 设置LinkedBlockingDeque大小为2, 则最大线程数量起作用:首先为前三个任务开启了核心线程1, 2, 3
		// 然后第四个任务和第五个任务被加入到队列中,
		// 第六个任务因为队列满了, 就直接创建了一个新的线程4, 此时共有4个线程, 没有超过最大线程数.
		// 8s后, 非核心线程超时被回收, 因此线程池只剩下3个线程了
		// ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5,
		// TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(2));

		// 设置LinkedBlockingDeque大小为1, 报错:直接出错在第6个execute方法上.因为核心线程数是3个,
		// 当加入第四个任务的时候, 就把第四个放在队列中.
		// 加入第五个任务时, 因为队列满了, 就创建新的线程执行, 创建了线程4.
		// 当加入第六个任务时, 也会尝试创建新线程, 但是因为已经达到了线程池最大线程数, 所以直接抛异常了.
		ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5,
				TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1));

		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println("========== start three thread ==========");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println("========== start three thread again ==========");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(8000);
		System.out.println("----after 8 seconds----");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(8000);
		System.out.println("----after 8 seconds, start 11 thread ----");
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(10000);
		executor.shutdown();
	}

	public static void test5() throws InterruptedException {

		// corePoolSize:3, maximumPoolSize:4,keepAliveTime:5s,
		// workQueue:SynchronousQueue
		// 报错分析: 添加第五个任务时报错, 因为SynchronousQueue根本不会保存任务, 收到一个任务就去创建新线程.
		ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5,
				TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println("========== start three thread ==========");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println("========== start three thread again ==========");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(8000);
		System.out.println("----after 8 seconds----");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(8000);
		System.out.println("----after 8 seconds, start 11 thread ----");
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());

		Thread.sleep(10000);
		executor.shutdown();
	}

	public static void test6() throws InterruptedException {
		// corePoolSize:2 maximumPoolSize:3 keepAliveTime:30s
		// workQueue:SynchronousQueue
		ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 30,
				TimeUnit.SECONDS, new SynchronousQueue<Runnable>(false));

		executor.execute(new MyRunnable());
		executor.execute(new MyRunnable());
		System.out.println("========== start two thread ==========");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());// Always
																				// returns
																				// zero.

		Thread.sleep(5000);

		executor.execute(new MyRunnable());
		// executor.execute(new MyRunnable());
		System.out
				.println("=====================================================");
		System.out.println(" core pool size::" + executor.getCorePoolSize());
		System.out.println("      pool size::" + executor.getPoolSize());
		System.out.println("task queue size::" + executor.getQueue().size());// Always
																				// returns
																				// zero.

		Thread.sleep(30000);
		executor.shutdown();
	}
}