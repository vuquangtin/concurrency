package threadpools.mycustom.pool;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public interface FailHandler<T> {

	/**
	 * 处理无法提交线程池执行的异步任务。
	 * 
	 * @param task
	 *            无法提交线程池执行的异步任务
	 */
	void execute(T task);

}