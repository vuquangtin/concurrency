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
public class ThreadPoolStatus {

	/** 未初始化 */
	public final static int UNINITIALIZED = 0;

	/** 初始化成功 */
	public final static int INITIALITION_SUCCESSFUL = 1;

	/** 初始化失败 */
	public final static int INITIALITION_FAILED = 2;

	/** 已销毁 */
	public final static int DESTROYED = 3;

}