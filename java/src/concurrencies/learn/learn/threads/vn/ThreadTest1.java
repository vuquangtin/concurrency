package learn.threads.vn;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadTest1 implements Runnable {
	public void run() {
		System.out.println("Make a thread…");
	}

	public static void main(String args[]) {
		ThreadTest1 m1 = new ThreadTest1();
		Thread t1 = new Thread(m1);
		t1.start();
	}
}