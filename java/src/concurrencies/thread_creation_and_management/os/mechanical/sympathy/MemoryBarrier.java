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
public class MemoryBarrier {
	static int sharedCounter = 0;

	public static void barrier(final int MAX) throws Exception {
		// First thread
		final Thread t1 = new Thread(() -> {
			for (int i = 0; i < MAX / 2; i++) {
				getUnsafe().loadFence(); // Read barrier
				sharedCounter++;
				getUnsafe().storeFence(); // Store barrier
			}
		});

		// Second thread
		final Thread t2 = new Thread(() -> {
			for (int i = 0; i < MAX / 2; i++) {
				getUnsafe().loadFence(); // Read barrier
				sharedCounter++;
				getUnsafe().storeFence(); // Store barrier
			}
		});

		// Start threads
		t1.start();
		t2.start();

		// Wait threads
		t1.join();
		t2.join();

		System.out.println("counter=" + sharedCounter);
	}

	private static MemoryBarrierObject getUnsafe() {

		return new MemoryBarrierObject();
	}
}
