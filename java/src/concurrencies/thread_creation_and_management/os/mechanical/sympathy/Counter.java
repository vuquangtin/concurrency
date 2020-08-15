package os.mechanical.sympathy;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Counter {
	public static void counter(int MAX) {
		int counter = 0;
		for (int i = 0; i < MAX; i++) {
			counter++;
		}

		System.out.println("counter=" + counter);
	}
	
	
}
