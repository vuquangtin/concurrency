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
public abstract class TaskHandler extends Task {

	private static final int WHAT = 0x0001;
	private static final String DATA = "byte";

	private long id;

	private DataHandler mHandler = new DataHandler();

	public TaskHandler(long id) {
		super(id);
		this.id = id;
	}

	public long getId() {
		return id;
	}

	@Override
	public void run() {
		byte[] data = request();
		mHandler.setTask(this);
		Message message = mHandler.obtainMessage(WHAT);
		Bundle bundle = new Bundle();
		bundle.putByteArray(DATA, data);
		message.setData(bundle);
		mHandler.sendMessage(message);
	}

	/**
	 * @return
	 */
	public abstract byte[] request();

	/**
	 * @param data
	 */
	public abstract void postResponse(byte[] data);

	private static class DataHandler extends Handler {
		private TaskHandler task;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT:
				Bundle bundle = msg.getData();
				task.postResponse(bundle.getByteArray(DATA));
				break;

			default:
				break;
			}
		}

		public void sendMessage(Message message) {
			// TODO Auto-generated method stub
			
		}

		public Message obtainMessage(int what) {
			// TODO Auto-generated method stub
			return null;
		}

		public void setTask(TaskHandler task) {
			this.task = task;
		}

	}

}