package lockobjects.reentrant;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyReadWriteLock {
	private int writeRequest = 0;
	private int read = 0;
	private int write = 0;

	public void readLock() throws InterruptedException {
		// 判断是否有写操作或写请求
		if (write > 0 || writeRequest > 0) {
			wait();
		}
		read++;
	}

	public void unreadLock() {
		read--;
		notifyAll();
	}

	public void writeRequest() throws InterruptedException { // 有多少线程请求写操作并无关系
		writeRequest++;
		if (read > 0 || write > 0) {
			wait();
		}

		writeRequest--;
		write++;
	}

	public void unwriteLock() {
		write--;
		notifyAll();
	}
}