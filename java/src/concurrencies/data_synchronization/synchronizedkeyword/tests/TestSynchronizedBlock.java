package synchronizedkeyword.tests;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class TestSynchronizedBlock implements Runnable {
	private int x;
	private int y;

	public static void main(String[] args) {
		TestSynchronizedBlock that = new TestSynchronizedBlock();
		(new Thread(that)).start();
		(new Thread(that)).start();
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			synchronized (this) {
				x++;
				y++;
			}
			System.out.println(Thread.currentThread().getName() + "x=" + x + "y=" + y);
		}
	}
}
