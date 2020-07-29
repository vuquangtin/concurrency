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
public class RunnableDemoTest {
	public static void main(String args[]) {
		System.out.println("Main thread running... ");

		RunnableDemo R1 = new RunnableDemo("Thread-1-HR-Database");
		R1.start();

		RunnableDemo R2 = new RunnableDemo("Thread-2-Send-Email");
		R2.start();

		System.out.println("==> Main thread stopped!!! ");
	}
}