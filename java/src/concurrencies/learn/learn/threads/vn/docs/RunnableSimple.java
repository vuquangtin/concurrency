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
public class RunnableSimple implements Runnable {
	public void run() {
		System.out.println("thread is running...");
	}

	public static void main(String args[]) {
		RunnableSimple runable = new RunnableSimple();
		Thread t1 = new Thread(runable);
		t1.start();
	}
}