package volatiles;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class NoAtoVolatile {

	private static volatile int value;

	public synchronized void increment() {
		value++;
		System.out.println(value);
	}

	public void increment2() {
		value++;
		System.out.println(value);
	}

	public static void main(String[] args) {
		final NoAtoVolatile volatile2 = new NoAtoVolatile();
		for (int i = 0; i < 10; i++) {
			new Thread(() -> volatile2.increment2()).start();
		}
	}
}