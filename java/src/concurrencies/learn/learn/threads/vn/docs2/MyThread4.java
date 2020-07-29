package learn.threads.vn.docs2;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MyThread4 {

	public static void main(String args[]) {
		Thread t1 = new Thread();
		Thread t2 = new Thread();
		System.out.println("ThreadName 1: " + t1.getName());
		System.out.println("ThreadName 2: " + t2.getName());
		t1.start();
		t2.start();
	}
}