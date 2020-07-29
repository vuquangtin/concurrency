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
public class MyThread2 {

	public static void main(String args[]) {
		DemoThread1 d1 = new DemoThread1();
		DemoThread2 d2 = new DemoThread2();

		Thread t1 = new Thread(d1);
		Thread t2 = new Thread(d2);
		System.out.println("Priority 1 : " + t1.getPriority());
		System.out.println("Priority 2 : " + t2.getPriority());
		t1.start();
		t2.start();
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