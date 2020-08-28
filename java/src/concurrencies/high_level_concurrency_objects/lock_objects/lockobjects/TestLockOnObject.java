package lockobjects;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class TestLockOnObject extends Thread {
	Object lock1 = new Object();
	int i1;

	public void run() {
		doit();
		check();
	}

	void doit() {
		synchronized (lock1) {
			System.out.println(Thread.currentThread().getName());
			i1++;
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		System.out.println("Finished by" + Thread.currentThread().getName());
	}

	void check() {
		System.out.println("Value of i1 is: " + i1);
		// System.out.println(Thread.currentThread().getName());
	}

	public static void main(String[] args) {
		new TestLockOnObject().start();
		new TestLockOnObject().start();
	}
}