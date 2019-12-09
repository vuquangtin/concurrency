package volatilekeyword;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class SharedObject implements Runnable {
	private volatile int count = 0;

	public void increamentCount() {
		count++;
	}

	public int getCount() {
		return count;
	}

	private static Object LOCK = new Object();

	public static void sleepWaitExamples() throws InterruptedException {

		Thread.sleep(1000);
		System.out.println("Thread '" + Thread.currentThread().getName()
				+ "' is woken after sleeping for 1 second");

		synchronized (LOCK) {
			LOCK.wait(1000);
			System.out.println("Object '" + LOCK + "' is woken after"
					+ " waiting for 1 second");
		}
		BObject b = new BObject();
		synchronized (b) {
			while (b.getSum() == 0) {
				System.out.println("Waiting for ThreadB to complete...");
				b.wait();
			}

			System.out.println("ThreadB has completed. "
					+ "Sum from that thread is: " + b.getSum());
		}

	}

	int sum;

	@Override
	public void run() {
		synchronized (this) {
			int i = 0;
			while (i < 100000) {
				sum += i;
				i++;
			}
			// se chay lai khi synchronized co chua wait()
			notify();
		}
	}
}