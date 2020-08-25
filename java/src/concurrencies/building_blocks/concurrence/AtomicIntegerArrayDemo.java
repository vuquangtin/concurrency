package concurrence;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class AtomicIntegerArrayDemo {

	static int[] counts = {0,0};

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++)
			new Thread(() -> {
				for (int j = 0; j < 10000; j++) {
					counts[0]++;
					counts[1]++;
				}
			}).start();
		Thread.sleep(3000);
		System.out.println("counts[0] = " + counts[0]);
		System.out.println("counts[1] = " + counts[1]);
	}
}
