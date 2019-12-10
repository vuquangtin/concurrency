package basic.jmm;

import java.util.Random;

/**
 * Concurrency.
 */
public class Concurrency {

	/**
	 * @param args
	 *            args.
	 */
	public static void main(String[] args) {
		SharedValue sharedValue = new SharedValue();

		new Thread(new Concurrent(sharedValue)).start();
		new Thread(new Concurrent(sharedValue)).start();
		try {
			Thread.sleep(1500);
			System.out.printf("[%s] %s%n", Thread.currentThread().getName(), sharedValue.value);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Concurrent.
	 */
	private static class Concurrent implements Runnable {

		final SharedValue sharedValue;

		/**
		 * @param sharedValue
		 *            shared value.
		 */
		Concurrent(SharedValue sharedValue) {
			this.sharedValue = sharedValue;
		}

		/**
		 * Run.
		 */
		@Override
		public void run() {
			try {
				Thread.sleep(new Random().nextInt(1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.sharedValue.value += 10;
			System.out.printf("[%s] %s%n", Thread.currentThread().getName(), this.sharedValue.value);
		}
	}

	/**
	 * SharedValue.
	 */
	private static class SharedValue {
		public Integer value = -10;
	}
}