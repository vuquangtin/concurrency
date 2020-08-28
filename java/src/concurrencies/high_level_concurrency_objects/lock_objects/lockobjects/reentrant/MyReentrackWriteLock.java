package lockobjects.reentrant;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyReentrackWriteLock {
	Map<Thread, Integer> readThread = new HashMap<Thread, Integer>();

	private int write = 0;
	private int writeRequest = 0;
	private Thread writeThread = null;

	public Boolean isWrite(Thread currentwriteThread) { // 判断当前是否有写线程，false为没有写线程，true表示有写线程
		return this.writeThread == currentwriteThread;
	}

	public Boolean hasreadsThread() {
		return readThread.size() > 0;
	}

	public Boolean getWriteAcess(Thread thread) {
		if (hasreadsThread()) {
			return false;
		}
		if (writeThread == null) {
			return true;
		}
		return isWrite(thread);
	}

	public void write() throws InterruptedException {
		Thread thread = Thread.currentThread();
		if (!getWriteAcess(thread)) {
			wait();
		}

		writeRequest--;
		write++;
	}

	public void unwrite() {
		write--;
		if (write == 0) {
			writeThread = null;
		}
		notifyAll();
	}
}
