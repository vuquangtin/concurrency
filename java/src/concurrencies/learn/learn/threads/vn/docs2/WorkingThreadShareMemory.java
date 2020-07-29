package learn.threads.vn.docs2;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class WorkingThreadShareMemory extends Thread {
	private ShareMemory mShareMemory;
	private String mThreadName;

	public WorkingThreadShareMemory(ShareMemory sm, String threadName) {
		this.mShareMemory = sm;
		this.mThreadName = threadName;
	}

	@Override
	public void run() {
		mShareMemory.printData(mThreadName);
	}
}
