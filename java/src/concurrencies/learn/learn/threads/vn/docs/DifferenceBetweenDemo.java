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
public class DifferenceBetweenDemo {
	public static void main(String[] args) throws InterruptedException {
		// Nhiều đối tượng thread
		UsingRunnable ur = new UsingRunnable();
		Thread t1 = new Thread(ur);
		t1.start();
		Thread.sleep(1000);
		Thread t2 = new Thread(ur);
		t2.start();
		Thread.sleep(1000);

		// Mỗi thread tương ứng với một thể hiện
		UsingThread ut1 = new UsingThread();
		ut1.start();
		Thread.sleep(1000);
		UsingThread ut2 = new UsingThread();
		ut2.start();
	}
}
