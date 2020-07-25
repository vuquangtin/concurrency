package threadpools.mycustom.pool;

import threadpools.mycustom.pool.common4j.ILifeCycle;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ThreadPoolManager implements ILifeCycle {

	private ILifeCycle _threadPool = new ThreadPoolImpl();

	private static Object _lock = new Object();
	private boolean _initStatus = false;
	private boolean _destroyStatus = false;
	private static ThreadPoolManager _instance = new ThreadPoolManager();

	public static ThreadPoolManager getSingleton() {
		return _instance;
	}

	public ThreadPool getThreadPool() {
		return (ThreadPool) _threadPool;
	}

	/**
	 *
	 * 用于单元测试和子类扩展
	 */
	protected void setThreadPool(ThreadPool threadPool) {
		this._threadPool = (ILifeCycle) threadPool;
	}

	@Override
	public void init() {
		synchronized (_lock) {
			if (_initStatus) {
				return;
			}
			_threadPool.init();
			_initStatus = true;
		}
	}

	@Override
	public void destroy() {
		synchronized (_lock) {
			if (_destroyStatus) {
				return;
			}
			_threadPool.destroy();
			_destroyStatus = true;
		}
	}

}