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
public class TestThreadRunnable {
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// Tạo ra luồng t1 từ class ThreadRunnableFramgia
		ThreadRunnableFramgia run1 = new ThreadRunnableFramgia();
		Thread t1 = new Thread(run1);
		t1.start();

		// Tạo ra luồng t2 từ class ThreadRunnableFramgia
		ThreadRunnableFramgia run2 = new ThreadRunnableFramgia();
		Thread t2 = new Thread(run2);
		t2.start();

		// Tạo ra luồng t3 từ class ThreadRunnableFramgia
		ThreadRunnableFramgia run3 = new ThreadRunnableFramgia();
		Thread t3 = new Thread(run3);
		t3.start();
	}
}
