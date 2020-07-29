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
public class MainFramgia {

	public static void main(String[] args) {
		// Tạo ra luồng t1 từ class ThreadFramgia
		ThreadFramgia t1 = new ThreadFramgia();
		// Xét thứ tự ưu tiên cao nhất cho luồng này
		t1.setPriority(Thread.MAX_PRIORITY);
		t1.start();

		// Tạo ra luồng t2 từ class ThreadFramgia
		ThreadFramgia t2 = new ThreadFramgia();
		// Xét thứ tự ưu tiên trung bình cho luồng này
		t2.setPriority(Thread.NORM_PRIORITY);
		t2.start();

		// Tạo ra luồng t3 từ class ThreadFramgia
		ThreadFramgia t3 = new ThreadFramgia();
		// Xét thứ tự ưu tiên thấp nhất cho luồng này
		t3.setPriority(Thread.MIN_PRIORITY);
		t3.start();
	}
}