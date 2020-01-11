package learn.threads.useexecutorservice;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import learn.threads.extendsthread.LearnExtendsThread;

/**
 * 线程池的使用： FixedThreadPool - 线程池大小固定，任务队列无界 SingleThreadExecutor -
 * 线程池大小固定为1，任务队列无界. CachedThreadPool - 线程池无限大（MAX INT），等待队列长度为1
 *
 * https://www.cnblogs.com/xiaoxi/p/7692250.html
 *
 */
public class useExecutorService {
	public static void main(String[] args) throws InterruptedException {
		// useFixedThreadPool();
		// useSingleThreadExecutor();
		// useCachedThreadPool();

		// 线程池中的多线程执行完毕,再执行主线程 CountDownLatch实现
		useCountDownLatch();

	}

	/**
	 * 固定线程大小,多余的排队 1）从线程池中获取可用线程执行任务，如果没有可用线程则使用ThreadFactory创建新的线程，
	 * 直到线程数达到nThreads(最大个数)。
	 * 
	 * 2）线程池线程数达到nThreads以后，新的任务将被放入队列。
	 * FixedThreadPool的优点是能够保证所有的任务都被执行，永远不会拒绝新的任务；
	 * 同时缺点是队列数量没有限制，在任务执行时间无限延长的这种极端情况下会造成内存问题。
	 */
	private static void useFixedThreadPool() {
		int threadNum = 2;
		// 创建固定大小的线程池
		ExecutorService executorService = Executors
				.newFixedThreadPool(threadNum);
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
		for (int i = 0; i < 5; i++) {
			Thread t = new LearnExtendsThread(i);
			threadPoolExecutor.execute(t);
			System.out.println("线程池中现在的线程数目是："
					+ threadPoolExecutor.getPoolSize() + ",  队列中正在等待执行的任务数量为："
					+ threadPoolExecutor.getQueue().size());
		}
		threadPoolExecutor.shutdown();
	}

	/**
	 * 只建一个,单线程处理,其它的排队 SingleThreadExecutor 适用于在逻辑上需要单线程处理任务的场景，
	 * 同时无界的LinkedBlockingQueue保证新任务都能够放入队列，不会被拒绝；
	 * 缺点和FixedThreadPool相同，当处理任务无限等待的时候会造成内存问题。
	 */
	private static void useSingleThreadExecutor() {
		// 创建一个单线程的线程池
		ExecutorService executor = Executors.newSingleThreadExecutor();

		for (int i = 1; i <= 5; i++) {
			Thread t = new LearnExtendsThread(i);
			// 将线程放到池中执行
			executor.execute(t);
		}
		// 关闭线程池
		executor.shutdown();
	}

	/**
	 * 来多少，建多少 CachedThreadPool对任务的处理策略是提交的任务会立即分配一个线程进行执行，
	 * 线程池中线程数量会随着任务数的变化自动扩张和缩减， 在任务执行时间无限延长的极端情况下会创建过多的线程。
	 */
	private static void useCachedThreadPool() {
		// 创建一个可缓存的线程池
		ExecutorService executor = Executors.newCachedThreadPool();
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;

		for (int i = 1; i <= 5; i++) {
			Thread t = new LearnExtendsThread(i);
			// 将线程放到池中执行
			threadPoolExecutor.execute(t);
			System.out.println("线程池中现在的线程数目是："
					+ threadPoolExecutor.getPoolSize() + ",  队列中正在等待执行的任务数量为："
					+ threadPoolExecutor.getQueue().size());
		}
		// 关闭线程池
		threadPoolExecutor.shutdown();
	}

	/**
	 * 实现多个子线程执行完毕,再执行主线程
	 * 
	 * @throws InterruptedException
	 */
	private static void useCountDownLatch() throws InterruptedException {
		int threadNum = 3;// 线程数目
		final CountDownLatch latch = new CountDownLatch(threadNum);
		for (int i = 0; i < threadNum; i++) {
			new Thread() {
				@Override
				public void run() {
					try {
						System.out.println(Thread.currentThread().getName()
								+ "  开始执行存储过程..");
						Thread.sleep(2000);
						System.out.println(Thread.currentThread().getName()
								+ "  存储过程执行完毕...");
						// 2、子线程执行完毕，计数减1
						latch.countDown();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
		System.out.println("等待子线程执行完毕...");
		// 3、 当前线程挂起等待
		latch.await();
		System.out.println("主线程执行完毕....");
	}
}
