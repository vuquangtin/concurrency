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
public class ThreadDemoTest {
	public static void main(String args[]) {
		System.out.println("Main thread running... ");

		Thread2Demo T1 = new Thread2Demo("Thread-1-HR-Database");
		T1.start();

		Thread2Demo T2 = new Thread2Demo("Thread-2-Send-Email");
		T2.start();

		System.out.println("==> Main thread stopped!!! ");
	}
}