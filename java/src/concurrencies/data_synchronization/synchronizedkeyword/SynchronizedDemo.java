package synchronizedkeyword;

public class SynchronizedDemo {
	// 共享变量
	private boolean ready = false;
	private int result = 0;
	private int number = 1;

	// 写操作
	public synchronized void write() {
		ready = true;
		number = 2;
	}

	// 读操作
	public synchronized void read() {
		if (ready) {
			result = number * 3;
		}
		System.out.println("result = " + result);
	}

	// 内部线程类
	private class ReadWriteThread extends Thread {
		private boolean mFlag;

		public ReadWriteThread(boolean flag) {
			this.mFlag = flag;
		}

		public void run() {
			if (mFlag) {
				write();
			} else {
				read();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
		// 启动线程执行写操作
		synchronizedDemo.new ReadWriteThread(true).start();

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 启动线程执行读操作
		synchronizedDemo.new ReadWriteThread(false).start();
	}

}