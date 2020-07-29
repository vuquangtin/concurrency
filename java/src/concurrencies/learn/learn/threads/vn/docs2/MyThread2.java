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
public class MyThread2 {

	public static void main(String args[]) {
		DemoThread1 d1 = new DemoThread1(); // khởi tạo đối tượng d1
		DemoThread2 d2 = new DemoThread2(); // khởi tạo đối tượng d2

		Thread t1 = new Thread(d1); // khởi tạo luồng t1 với đối truyền vào là
									// d1
		Thread t2 = new Thread(d2); // khởi tạo luồng t2 với đối truyền vào là
									// d2
		t1.start(); // chạy luồng 1
		t2.start(); // chạy luồng 2
	}
}

class DemoThread1 implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 500; i++) {
			System.out.println(" thread1 " + i);
		}
	}
}

class DemoThread2 implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 500; i++) {
			System.out.println(" thread2 " + i);
		}
	}
}
