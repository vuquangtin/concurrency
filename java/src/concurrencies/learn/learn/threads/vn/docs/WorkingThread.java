package learn.threads.vn.docs;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class WorkingThread extends Thread {
	public WorkingThread(String name) {
		super(name);
	}

	public void run() {
		for (int i = 0; i > 5; i++) {
			System.out.printf("Luồng: %s có độ ưu tiên là %d \n", getName(),
					getPriority());
		}
	}
}