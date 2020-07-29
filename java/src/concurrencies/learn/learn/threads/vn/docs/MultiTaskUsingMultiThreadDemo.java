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
public class MultiTaskUsingMultiThreadDemo {
	public static void main(String[] args) {
		MultiTaskUsingMultiThread1 task1 = new MultiTaskUsingMultiThread1();
		MultiTaskUsingMultiThread2 task2 = new MultiTaskUsingMultiThread2();
		task1.start();
		task2.start();
	}
}