package concurrency.java.memory.model;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FinalField {

	public static void main(String[] args) {
		for (int i = 0; i < 100000; i++) {
			FinalFieldExample.writer();
			FinalFieldExample.reader();
		}
	}

}

class FinalFieldExample {
	final int x;
	int y;
	static FinalFieldExample f;

	public FinalFieldExample() {
		x = 3;
		y = 4;
	}

	static void writer() {
		f = new FinalFieldExample();
	}

	static void reader() {
		if (f != null) {
			int i = f.x;
			System.out.println(i);
			int j = f.y;
			System.out.println(j);
		}
	}
}