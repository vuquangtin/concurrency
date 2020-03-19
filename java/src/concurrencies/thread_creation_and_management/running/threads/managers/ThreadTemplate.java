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
public abstract class ThreadTemplate {
	private static final String TAG = "ThreadTemplate";
	private static final int THREAD_PRIORITY_BACKGROUND=0;
	private static TaskManager getTaskManager(TaskManagerType type) {
		TaskManager taskManager = null;
		if (type == null) {
			System.out.print("!!!getTaskManager type is null");
			return null;
		}

		switch (type) {
		case FILE_UPLOAD:
			taskManager = PoolCache.getInstance().getFileTaskManager();
			break;
		case CLOUND_FILE_UPLOAD:
			taskManager = PoolCache.getInstance().getBatchFileUploadManager();
			break;
		case COMMON:
			taskManager = PoolCache.getInstance().getTaskManager();
			break;
		case LOG:
			taskManager = PoolCache.getInstance().getLogManager();
			break;
		case IM:
			taskManager = PoolCache.getInstance().getImManger();
			break;
		case NET_REQUEST:
			taskManager = PoolCache.getInstance().getRequestManager();
			break;
		case IM_CHAT:
			PoolCache.getInstance().initImChatManager();
			taskManager = PoolCache.getInstance().getImChatManger();
			break;
		default:
			break;
		}
		return taskManager;
	}

	/**
	 * 用于启动线程;
	 *
	 * @param callback
	 *            Callback
	 * @param type
	 *            TaskManagerType
	 * @return taskId long
	 */
	public long startThread(Callback callback, TaskManagerType type) {

		final TaskManagerType tmType = type;

		long taskId = ThreadPoolUtils.generateRandom();
		Task task = new Task(taskId) {
			@Override
			public void run() {
				//Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND);
				sendRequest();
			}
		};

		TaskManager taskManager = getTaskManager(type);
		try {
			if (!taskManager.isDestory()) {
				taskManager.putTask(task);
			} else {
				System.out.print(String.format(
						"Thread pool is destory, type = %s", type.name()));
			}
		} catch (InterruptedException e) {
			if (callback != null) {
				Callback.CallbackInfo<Integer> callbackInfo = new Callback().new CallbackInfo<>();
				callbackInfo.bError = true;
				callbackInfo.errorCode = 15;// NetError.THREAD_INTERRUPTED;
				callback.callback(callbackInfo);
			} else {
				// callback is null
			}
			// LogCore.i(TAG, Log.getStackTraceString(e));
		}

		return taskId;
	}

	public long startCommonThread() {
		return startCommonThread(null);
	}

	public long startCommonThread(Callback callback) {
		return startThread(callback, TaskManagerType.COMMON);
	}

	public long startFileThread() {
		return startFileThread(null);
	}

	public long startFileThread(Callback callback) {
		return startThread(callback, TaskManagerType.FILE_UPLOAD);
	}

	public long startCloundFileThread(Callback callback) {
		return startThread(callback, TaskManagerType.CLOUND_FILE_UPLOAD);
	}

	public long startImThread() {
		return startThread(null, TaskManagerType.IM);
	}

	public long startImChat() {
		return startThread(null, TaskManagerType.IM_CHAT);
	}

	public long startNetRequestThread(Callback callback) {
		return startThread(callback, TaskManagerType.NET_REQUEST);
	}

	/**
	 * 启动日志线程
	 * 
	 * @return long
	 */
	public long startLogThread() {
		return startThread(null, TaskManagerType.LOG);
	}

	public abstract void sendRequest();

	public enum TaskManagerType {
		FILE_UPLOAD, CLOUND_FILE_UPLOAD, COMMON, LOG, IM, IM_CHAT, // 在IM界面使用的线程池
		NET_REQUEST
	}

	/**
	 * 用于启动线程;
	 *
	 * @param callback
	 *            Callback
	 * @param type
	 *            TaskManagerType
	 * @return taskId long
	 */
	public static long startThread(Callback callback, final Runnable runnable,
			ThreadTemplate.TaskManagerType type) {

		final ThreadTemplate.TaskManagerType tmType = type;

		long taskId = ThreadPoolUtils.generateRandom();
		Task task = new Task(taskId) {
			@Override
			public void run() {
				//Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND);
				runnable.run();
			}
		};

		TaskManager taskManager = getTaskManager(type);
		try {
			if (!taskManager.isDestory()) {
				taskManager.putTask(task);
			} else {
//				LogCore.i(
//						TAG,
//						String.format("Thread pool is destory, type = %s",
//								type.name()));
			}
		} catch (InterruptedException e) {
			if (callback != null) {
				Callback.CallbackInfo<Integer> callbackInfo = new Callback().new CallbackInfo<>();
				callbackInfo.bError = true;
				callbackInfo.errorCode = 15;// NetError.THREAD_INTERRUPTED;
				callback.callback(callbackInfo);
			} else {
				// callback is null
			}
			// LogCore.i(TAG, Log.getStackTraceString(e));
		}

		return taskId;
	}
}