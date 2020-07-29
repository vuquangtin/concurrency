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
public class OneTaskUsingMultiThread implements Runnable {

	@Override
	public void run() {
		System.out.println("Xu ly mot tac vu su dung nhieu thread");
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new OneTaskUsingMultiThread());
		Thread t2 = new Thread(new OneTaskUsingMultiThread());
		t1.start();
		t2.start();
	}
}
