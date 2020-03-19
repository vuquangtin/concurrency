package running.threads.managers;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class PoolCache {

	// 提供给通常的曾删改查操作使用
	private static TaskManager mTaskManager = null;
	// 提供给上传文件操作
	private static TaskManager mFileTaskManager = null;

	private static TaskManager mLogManager = null;

	private static TaskManager mImManager = null;

	private static TaskManager mImChatManager = null;

	private static TaskManager mRequestManager = null;

	private TaskManager mBatchFileUploadManager = null;

	private static PoolCache mInstance = null;

	private PoolCache() {
		initTaskManager();

		initFileTaskManager();

		initLogManager();

		initImManager();

		initRequestManager();
	}

	public static PoolCache getInstance() {
		if (mInstance == null) {
			mInstance = new PoolCache();
		}
		return mInstance;
	}

	private void initFileTaskManager() {
		TaskManager.TaskManagerParams params = new TaskManager.TaskManagerParams();
		params.threadPoolSize = 2;
		mFileTaskManager = TaskManager.newInstance("file", params);
	}

	public void initBatchFileUploadManager() {
		TaskManager.TaskManagerParams params = new TaskManager.TaskManagerParams();
		params.threadPoolSize = 3;
		mBatchFileUploadManager = TaskManager.newInstance("batch_file_upload",
				params);
	}

	private void initTaskManager() {
		TaskManager.TaskManagerParams params = new TaskManager.TaskManagerParams();
		params.threadPoolSize = 2;
		mTaskManager = TaskManager.newInstance("common", params);
	}

	private void initLogManager() {
		TaskManager.TaskManagerParams params = new TaskManager.TaskManagerParams();
		params.threadPoolSize = 1;
		mLogManager = TaskManager.newInstance("log", params);
	}

	private void initImManager() {
		TaskManager.TaskManagerParams params = new TaskManager.TaskManagerParams();
		params.threadPoolSize = 1;
		mImManager = TaskManager.newInstance("IM", params);
	}

	public void initImChatManager() {
		if (mImChatManager == null || mImChatManager.isDestory()) {
			TaskManager.TaskManagerParams params = new TaskManager.TaskManagerParams();
			params.threadPoolSize = 1;
			mImChatManager = TaskManager.newInstance("IM_CHAT", params);
		}
	}

	private void initRequestManager() {
		TaskManager.TaskManagerParams params = new TaskManager.TaskManagerParams();
		params.threadPoolSize = 5;
		mRequestManager = TaskManager.newInstance("net_request", params);
	}

	public TaskManager getTaskManager() {
		return mTaskManager;
	}

	public TaskManager getFileTaskManager() {
		return mFileTaskManager;
	}

	public TaskManager getLogManager() {
		return mLogManager;
	}

	public TaskManager getImManger() {
		return mImManager;
	}

	public TaskManager getImChatManger() {
		return mImChatManager;
	}

	public TaskManager getRequestManager() {
		return mRequestManager;
	}

	public TaskManager getBatchFileUploadManager() {
		if (mBatchFileUploadManager == null
				|| mBatchFileUploadManager.isDestory()) {
			initBatchFileUploadManager();
		}
		return mBatchFileUploadManager;
	}

}