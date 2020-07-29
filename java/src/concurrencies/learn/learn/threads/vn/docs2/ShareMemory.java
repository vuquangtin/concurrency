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
public class ShareMemory {
	public synchronized void printData(String threadName) {
		for (int i = 1; i <= 5; i++) {
			System.out.println(threadName + ": " + i);
		}
	}
}
