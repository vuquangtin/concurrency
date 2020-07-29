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
public class MyThread3 {

	public static void main(String args[]) {
		Thread t1 = new Thread(); // tạo luồng t1
		Thread t2 = new Thread(); // tao luồng t2
		System.out.println("ThreadID1 : " + t1.getId());
		System.out.println("ThreadID2 : " + t2.getId());
	}
}