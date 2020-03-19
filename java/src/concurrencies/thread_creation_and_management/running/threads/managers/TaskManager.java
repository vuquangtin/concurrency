package running.threads.managers;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TaskManager {

	static final String TAG = "TaskManager";
	String taskManagerName = null;

	private static TaskManager mTaskManager;

	private ThreadPoolManager mThreadPoolManager;

	private BlockingQueue<Task> mqueue = new ArrayBlockingQueue<>(5);
	private TaskProducer mProducer = null;
	private TaskConsumer mConsumer = null;

	private RunConsumer mRunConsumer;

	private boolean isDestory = false;

	private TaskManager(String name) {
		this.taskManagerName = name;
		mProducer = new TaskProducer(mqueue);
		mConsumer = new TaskConsumer(mqueue);

		mThreadPoolManager = new ThreadPoolManager();
		mThreadPoolManager.createPool(taskManagerName);

		isDestory = false;

		mRunConsumer = new RunConsumer();
		new Thread(mRunConsumer, taskManagerName + "-consumer").start();

	}

	private TaskManager(String name, TaskManagerParams params) {
		this.taskManagerName = name;
		mProducer = new TaskProducer(mqueue);
		mConsumer = new TaskConsumer(mqueue);

		mThreadPoolManager = new ThreadPoolManager();
		mThreadPoolManager.createPool(params.threadPoolSize, taskManagerName);

		isDestory = false;

		mRunConsumer = new RunConsumer();
		new Thread(mRunConsumer, taskManagerName + "-consumer").start();

	}

	public static TaskManager getInstance(String name) {
		if (mTaskManager == null) {
			mTaskManager = new TaskManager(name);
		}
		return mTaskManager;
	}

	public static TaskManager getInstance(String name, TaskManagerParams params) {
		if (mTaskManager == null) {
			mTaskManager = new TaskManager(name, params);
		}
		return mTaskManager;
	}

	public static TaskManager newInstance(String name) {

		return new TaskManager(name);

	}

	public static TaskManager newInstance(String name, TaskManagerParams params) {

		return new TaskManager(name, params);

	}

	public void putTask(Task task) throws InterruptedException {
		if (mProducer != null) {
			mProducer.put(task);
		}
	}

	public void destoryThreadPool() {
		if (mThreadPoolManager != null) {
			mThreadPoolManager.destoryPool();
		}
		isDestory = true;
		if (mRunConsumer != null) {
			mRunConsumer.setCancel(true);
		}
	}

	/**
	 * 判断线程驰池是否销毁
	 *
	 * @return boolean
	 */
	public boolean isDestory() {
		return isDestory;
	}

	public static class TaskManagerParams {
		public int threadPoolSize;
	}

	private class RunConsumer implements Runnable {

		private boolean mCancel = false;

		public RunConsumer() {
			super();
		}

		@Override
		public void run() {
			while (!mCancel) {
				try {
					Task task = mConsumer.take();
					Future<?> future = mThreadPoolManager.execute(task);
				} catch (InterruptedException e) {
					// LogCore.i(TAG, android.util.Log.getStackTraceString(e));
				}
			}
		}

		public void setCancel(boolean mCancel) {
			this.mCancel = mCancel;
		}

	}

}