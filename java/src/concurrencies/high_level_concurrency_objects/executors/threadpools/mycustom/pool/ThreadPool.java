package threadpools.mycustom.pool;

import java.lang.management.ThreadInfo;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public interface ThreadPool {

	/**
	 * 提交一个不需要返回值的异步任务给默认的线程池执行。
	 * 
	 * @param task
	 * @return
	 */
	Future<?> submit(Runnable task);

	/**
	 * 提交一个不需要返回值的异步任务给指定的线程池执行。
	 * 
	 * @param task
	 * @param threadPoolName
	 * @return
	 */
	Future<?> submit(Runnable task, String threadPoolName);

	/**
	 * 提交一个不需要返回值的异步任务给指定的线程池执行。
	 * 
	 * @param task
	 *            实现了{@link Runnable}接口的异步任务
	 * @param threadPoolName
	 *            线程池名称
	 * @param failHandler
	 *            当队列满，异步任务无法提交给线程池执行的"失败处理器"
	 * @return 异步任务执行的结果。如果队列满导致任务无法提交，将返回null
	 */
	Future<?> submit(Runnable task, String threadPoolName, FailHandler<Runnable> failHandler);

	/**
	 * 提交一个需要返回值的异步任务给默认的线程池执行。
	 * 
	 * @param task
	 *            实现了{@link Callable}接口的异步任务
	 * @return 异步任务执行的结果
	 */
	<T> Future<T> submit(Callable<T> task);

	/**
	 * 提交一个需要返回值的异步任务给指定的线程池执行。
	 * 
	 * @param task
	 *            实现了{@link Callable}接口的异步任务
	 * @param threadPoolName
	 *            线程池名称
	 * @return 异步任务执行的结果
	 */
	<T> Future<T> submit(Callable<T> task, String threadPoolName);

	/**
	 * 提交一个需要返回值的异步任务给指定的线程池执行。
	 * 
	 * @param task
	 *            实现了{@link Callable}接口的异步任务
	 * @param threadPoolName
	 * @param failHandler
	 *            当队列满，异步任务无法提交给线程池执行的"失败处理器"
	 * @return 异步任务执行的结果。如果队列满导致任务无法提交，将返回null
	 */
	<T> Future<T> submit(Callable<T> task, String threadPoolName, FailHandler<Callable<T>> failHandler);

	/**
	 * 在线程池"default"中执行多个需要返回值的异步任务，并设置超时时间。
	 * 
	 * @param tasks
	 *            实现了{@link Runnable}接口的异步任务列表
	 * @param timeout
	 *            任务执行超时时间
	 * @param timeoutUnit
	 *            超时时间的单位
	 * @return {@link Future}列表。注：如果在指定的时间内，有任务没有执行完，在执行Future.get操作时将抛出
	 *         {@link CancellationException}。
	 */
	<T> List<Future<T>> invokeAll(Collection<Callable<T>> tasks, long timeout, TimeUnit timeoutUnit);

	/**
	 * 在指定的线程池中执行多个需要返回值的异步任务，并设置超时时间。
	 * 
	 * @param tasks
	 *            实现了{@link Runnable}接口的异步任务列表
	 * @param timeout
	 *            任务执行超时时间
	 * @param timeoutUnit
	 *            超时时间的单位
	 * @param threadPoolName
	 * @return {@link Future}列表。注：如果在指定的时间内，有任务没有执行完，在执行Future.get操作时将抛出
	 *         {@link CancellationException}。
	 */
	<T> List<Future<T>> invokeAll(Collection<Callable<T>> tasks, long timeout, TimeUnit timeoutUnit,
			String threadPoolName);

	/**
	 * 查询指定名称的线程池是否存在。
	 * 
	 * @param threadPoolName
	 *            线程池名称
	 * @return 如果指定的线程池存在，返回true；否则，返回true。
	 */
	boolean isExists(String threadPoolName);

	/**
	 * 获取线程池的信息。如：线程池容量，队列容量。
	 * 
	 * @param threadPoolName
	 * @return 线程池的信息({@link ThreadInfo})
	 */
	ThreadPoolInfo getThreadPoolInfo(String threadPoolName);

}