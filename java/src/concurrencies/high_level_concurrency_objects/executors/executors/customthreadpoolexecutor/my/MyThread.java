package executors.customthreadpoolexecutor.my;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyThread extends Thread {
	private MyTask _task;
	private boolean _isCoreThread;
	private MyThreadPoolExecutor _threadPoolExecutor;

	public MyThread(MyTask task, MyThreadPoolExecutor threadPoolExecutor, boolean isCoreThread) {
		this._task = task;
		this._isCoreThread = isCoreThread;
		this._threadPoolExecutor = threadPoolExecutor;
	}

	private MyTask getNewTask() {
		return (_task != null) ? _task : _threadPoolExecutor.getTaskQueue().get();
	}

	private boolean isValid(MyTask task) {
		return task != null;
	}

	private void runTask(MyTask task) {
		task.run();
		this._task = null;
	}

	private boolean isEmptyTaskQueue() {
		return _threadPoolExecutor.getTaskQueue().isEmpty();
	}

	public void run() {
		while (_threadPoolExecutor.isActive()) {
			MyTask task = getNewTask();
			if (isValid(task)) {
				runTask(task);
			} else {
				try {
					synchronized (_threadPoolExecutor) {
						_threadPoolExecutor.wait(_isCoreThread ? 0 : _threadPoolExecutor.getKeepAliveTime());
						if (isEmptyTaskQueue()) {
							_threadPoolExecutor.removeFromThreadPool(this);
							return;
						} else {
							_threadPoolExecutor.getTaskQueue().get().run();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
}
