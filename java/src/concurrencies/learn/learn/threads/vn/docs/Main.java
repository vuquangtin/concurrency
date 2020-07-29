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
public class Main {
	public static void main(String[] args) {
		// Tạo ra luồng t1 từ class ThreadFramgia
		ThreadFramgia t1 = new ThreadFramgia();
		t1.start();

		// Tạo ra luồng t2 từ class ThreadFramgia
		ThreadFramgia t2 = new ThreadFramgia();
		t2.start();

		// Tạo ra luồng t3 từ class ThreadFramgia
		ThreadFramgia t3 = new ThreadFramgia();
		t3.start();
	}
}
