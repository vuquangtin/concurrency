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
public class MyThread1 {

	public static void main(String args[]) {
		Thread1 t1 = new Thread1(); // khởi tạo đối tượng t1
		Thread2 t2 = new Thread2(); // khởi tạo đối tượng t2
		t1.start(); // bắt đầu chạy t1
		t2.start(); // bắt đầu chạy t2
	}
}

class Thread1 extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 500; i++) {
			System.out.println(" thread1 " + i);
		}
	}
}

class Thread2 extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 500; i++) {
			System.out.println(" thread2 " + i);
		}
	}
}