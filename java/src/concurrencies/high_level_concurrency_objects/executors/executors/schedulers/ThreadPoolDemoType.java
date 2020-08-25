package executors.schedulers;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import concurrencies.utilities.TimeUtils;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadPoolDemoType {

	/**
	 * 线程池概念： corePoolSize：核心线程数，即使线程处于空闲也会在池中保持的线程。当提交一个任务时，线程池会创建一个线程来执行任务，
	 * 直到线程数达到corePoolSize。然后新的任务会被添加到队列中。
	 * maximumPoolSize：池中允许存在的最大线程数。如果队列已满且线程数没有达到maximumPoolSize
	 * ，则会创建新线程。如果是无边界队列，则这个数没意义。
	 * keepAliveTime：当线程的数量超过corePoolSize时，允许线程在接受到任务之前最大的等待时间
	 * ，如果超过这个时间并且依然处于空闲状态，就会被销毁。
	 * workQueue：在任务没有被执行之前用来保存任务的队列。ArrayBlockingQueue
	 * ，LinkedBlockingQuene，SynchronousQuene，priorityBlockingQuene
	 * RejectedExecutionHandler
	 * ：当阻塞队列满了，且没有空闲的工作线程，如果继续提交任务，必须采取一种策略处理该任务。AbortPolicy
	 * ，CallerRunsPolicy，DiscardOldestPolicy，DiscardPolicy
	 */
	/**
	 * 缓冲线程池（多线程可变线程池）。 corePoolSize线程数：0； maximumPoolSize线程数：Integer.MAX_VALUE；
	 * 按需创建线程池。如果你的程序运行的是生命周期较短的异步任务，该类型的线程池通常能够提高性能。
	 * 如果池中线程可用的话，会进行重用。否则会创建新的线程。
	 * 如果一个线程在60秒内没有执行任务的话，它会被销毁并从池中移除，因此这样的线程池长时间空闲也不会消耗任何资源。
	 */
	@Test
	public void newCachedThreadPool() {
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
	}

	/**
	 * 缓冲线程池（多线程可变线程池）。 corePoolSize线程数：0； maximumPoolSize线程数：Integer.MAX_VALUE；
	 * 可以提供一个线程工厂。
	 */
	@Test
	public void newCachedThreadPoolWithThreadFactory() {
		ThreadFactory threadFactory = new ThreadFactory() {
			public Thread newThread(Runnable r) {
				String name = "I'm thread "
						+ String.valueOf(new Random().nextInt());
				return new Thread(r, name);
			}
		};
		ExecutorService newCachedThreadPool = Executors
				.newCachedThreadPool(threadFactory);

		for (int i = 0; i < 10; i++) {
			newCachedThreadPool.submit(new Runnable() {

				public void run() {
					System.out.println(Thread.currentThread().getName());
				}
			});
		}
	}

	/**
	 * 固定数量的线程池(多线程不可变线程池)。
	 * 创建固定数量n个线程的线程池。这些线程会操作一个共享的无边界队列。在任意时候最多只有n个线程执行任务。多余的任务会进入队列
	 * ，直到有可用的线程去执行。 corePoolSize线程数：nThreads； maximumPoolSize线程数：nThreads；
	 * 
	 */
	@Test
	public void newFixedThreadPool() {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
	}

	/**
	 * 固定数量的线程池(多线程不可变线程池)。允许自定义线程工厂
	 * 创建固定数量n个线程的线程池。这些线程会操作一个共享的无边界队列。在任意时候最多只有n个线程执行任务
	 * 。多余的任务会进入队列，直到有可用的线程去执行。 corePoolSize线程数：nThreads；
	 * maximumPoolSize线程数：nThreads；
	 * 
	 */
	@Test
	public void newFixedThreadPoolWithThreadFactory() {
		ThreadFactory threadFactory = new ThreadFactory() {
			public Thread newThread(Runnable r) {
				String name = "I'm thread "
						+ String.valueOf(new Random().nextInt());
				return new Thread(r, name);
			}
		};
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3,
				threadFactory);
	}

	/**
	 * 单线程线程池(单线程不可变线程池)。允许自定义线程工厂。
	 * 创建固定数量1个线程的线程池。这些线程会操作一个共享的无边界队列。如果一个线程终止则新的线程会被创建执行后续的任务
	 * 。在任何时刻都只有一个任务处于执行状态。保证任务按照顺序执行。
	 * 不同于newFixedThreadPool(1)，newSingleThreadExecutor()返回的线程池不允许再进行配置来使用更多的线程。
	 * corePoolSize线程数：1； maximumPoolSize线程数：1；
	 */
	@Test
	public void newSingleThreadExecutor() {
		ExecutorService newSingleThreadExecutor = Executors
				.newSingleThreadExecutor();
	}

	/**
	 * 单线程线程池(单线程不可变线程池)。允许自定义线程工厂。
	 * 创建固定数量1个线程的线程池。这些线程会操作一个共享的无边界队列。如果一个线程终止则新的线程会被创建执行后续的任务
	 * 。在任何时刻都只有一个任务处于执行状态。保证任务按照顺序执行。
	 * 不同于newFixedThreadPool(1)，newSingleThreadExecutor()返回的线程池不允许再进行配置来使用更多的线程。
	 * corePoolSize线程数：1； maximumPoolSize线程数：1；
	 */
	@Test
	public void newSingleThreadExecutorWithThreadFactory() {
		ThreadFactory threadFactory = new ThreadFactory() {
			public Thread newThread(Runnable r) {
				String name = "I'm thread "
						+ String.valueOf(new Random().nextInt());
				return new Thread(r, name);
			}
		};
		ExecutorService newSingleThreadExecutor = Executors
				.newSingleThreadExecutor(threadFactory);
	}

	/**
	 * 多线程可变调度线程池。调度规则：指定延迟过后开始执行任务。
	 * 创建的线程池允许你对任务进行调度。可以指定任务在延迟指定时间后执行，或者让任务周期性的执行。 参数corePoolSize
	 * ：即使线程处于空闲也会在池中保持的线程 延迟指定时间后执行
	 */
	@Test
	public void newScheduledThreadPoolWithDelayTask() {
		ScheduledExecutorService newScheduledThreadPool = Executors
				.newScheduledThreadPool(3);
		Runnable command = new Runnable() {

			public void run() {
				System.out.println("I am a runnable task.");
			}
		};
		long delay = 5;
		TimeUnit unit = TimeUnit.SECONDS;
		newScheduledThreadPool.schedule(command, delay, unit);
		TimeUtils.sleepMiliSeconds(1000, "");
	}

	/**
	 * 多线程可变调度线程池。调度规则：上一个任务开始时开始计时，period时间过去后，检测上一个任务是否执行完毕，如果上一个任务执行完毕，
	 * 则当前任务立即执行，如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行。
	 * 创建的线程池允许你对任务进行调度。可以指定任务在延迟指定时间后执行，或者让任务周期性的执行。该线程池不限制创建的线程数。
	 * 参数corePoolSize ：即使线程处于空闲也会在池中保持的线程 如果任务出现异常，则后续任务不会在执行。
	 * */
	@Test
	public void newScheduledThreadPoolAtFixedRate() {
		ScheduledExecutorService newScheduledThreadPool = Executors
				.newScheduledThreadPool(3);
		Runnable command = new Runnable() {

			public void run() {
				System.out.println("I am a runnable task."
						+ System.currentTimeMillis());
				try {
					Thread.sleep(1000 * 1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// //如果任务出现异常，则后续任务不会在执行。
				// double i=1/0;
			}
		};
		TimeUnit unit = TimeUnit.SECONDS;
		long initialDelay = 3;
		long period = 2;
		// 在延迟5秒后开始执行，然后每2秒执行一次
		newScheduledThreadPool.scheduleAtFixedRate(command, initialDelay,
				period, unit);
		TimeUtils.sleepMiliSeconds(1000, "");
	}

	/**
	 * 多线程可变调度线程池。调度规则：上一个任务结束时开始计时，delay时间过去后，立即执行。任务的再次执行需要在上次执行完毕后间隔指定时间。
	 * 创建的线程池允许你对任务进行调度。可以指定任务在延迟指定时间后执行，或者让任务周期性的执行。该线程池不限制创建的线程数。
	 * 参数corePoolSize ：即使线程处于空闲也会在池中保持的线程 如果任务出现异常，则后续任务不会在执行。
	 */
	@Test
	public void newScheduledThreadPoolWithFixedDelay() {
		ScheduledExecutorService newScheduledThreadPool = Executors
				.newScheduledThreadPool(3);
		Runnable command = new Runnable() {

			public void run() {
				System.out.println("I am a runnable task."
						+ System.currentTimeMillis());
				try {
					Thread.sleep(1000 * 5);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 如果任务出现异常，则后续任务不会在执行。
				// double i=1/0;
			}
		};
		TimeUnit unit = TimeUnit.SECONDS;
		long initialDelay = 5;
		long delay = 2;
		// 在延迟5秒后开始执行，然后每2秒执行一次
		newScheduledThreadPool.scheduleWithFixedDelay(command, initialDelay,
				delay, unit);
		TimeUtils.sleepMiliSeconds(1000, "");
	}

	/**
	 * 多线程可变调度线程池,自定义线程工厂
	 * 
	 */
	@Test
	public void newScheduledThreadPoolWithThreadFactory() {
		ThreadFactory threadFactory = new ThreadFactory() {
			public Thread newThread(Runnable r) {
				String name = "I'm thread "
						+ String.valueOf(new Random().nextInt());
				return new Thread(r, name);
			}
		};
		ScheduledExecutorService newScheduledThreadPool = Executors
				.newScheduledThreadPool(3, threadFactory);
	}

	/**
	 * 多线程可变调度线程池：延迟执行Callable，并获取结果
	 */
	@Test
	public void newScheduledThreadPoolWithCallable() {
		ScheduledExecutorService newScheduledThreadPool = Executors
				.newScheduledThreadPool(3);
		Callable callable = new Callable<String>() {

			public String call() throws Exception {
				String name = "I'm thread "
						+ String.valueOf(new Random().nextInt());
				return name;
			}
		};
		long delay = 2;
		TimeUnit unit = TimeUnit.SECONDS;
		ScheduledFuture schedule = newScheduledThreadPool.schedule(callable,
				delay, unit);
		try {
			Object object = schedule.get(3, unit);
			System.out.println(object);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 单线程调度线程池(单线程不可变调度线程池)。允许自定义线程工厂。
	 * 创建固定数量1个线程的线程池。这些线程会操作一个共享的无边界队列。如果一个线程终止则新的线程会被创建执行后续的任务
	 * 。在任何时刻都只有一个任务处于执行状态。保证任务按照顺序执行。
	 * 不同于newScheduledThreadPool(1)，newSingleThreadScheduledExecutor
	 * ()返回的线程池不允许再进行配置来使用更多的线程。 corePoolSize线程数：0；
	 * maximumPoolSize线程数：Integer.MAX_VALUE；
	 */
	@Test
	public void newSingleThreadScheduledExecutor() {
		ScheduledExecutorService newSingleThreadScheduledExecutor = Executors
				.newSingleThreadScheduledExecutor();
		Callable callable = new Callable<String>() {

			public String call() throws Exception {
				String name = "I'm thread "
						+ String.valueOf(new Random().nextInt());
				return name;
			}
		};
		long delay = 2;
		TimeUnit unit = TimeUnit.SECONDS;
		ScheduledFuture schedule = newSingleThreadScheduledExecutor.schedule(
				callable, delay, unit);
		try {
			Object object = schedule.get(3, unit);
			System.out.println(object);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 1.8之后新增
	 */
	@Test
	public void newWorkStealingPool() {
		// ExecutorService newWorkStealingPool =
		// Executors.newWorkStealingPool();
	}
}