package thread.objects;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ThreadExample extends Thread {

	// run() method contains the code that is executed by the thread.
	@Override
	public void run() {
		System.out.println("Inside : " + Thread.currentThread().getName());
	}

	public static void main(String[] args) {
		System.out.println("Inside : " + Thread.currentThread().getName());

		System.out.println("Creating thread...");
		Thread thread = new ThreadExample();

		System.out.println("Starting thread...");
		thread.start();
	}
}