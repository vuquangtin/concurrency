package synchronizers.exchanger.demo;

import java.util.Random;
import java.util.concurrent.Exchanger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExchangerTest {

	public static void main(String[] args) {

		Exchanger<String> exchanger = new Exchanger<>();
		ExchangerRunnable exchangerRunnable1 = new ExchangerRunnable(exchanger,
				"A");
		ExchangerRunnable exchangerRunnable2 = new ExchangerRunnable(exchanger,
				"B");

		new Thread(exchangerRunnable1).start();
		new Thread(exchangerRunnable2).start();
	}

	static class ExchangerRunnable implements Runnable {

		Exchanger<String> exchanger = null;
		String object = null;

		public ExchangerRunnable(Exchanger<String> exchanger, String object) {
			this.exchanger = exchanger;
			this.object = object;
		}

		@Override
		public void run() {
			try {
				Object previous = this.object;
				Thread.sleep(new Random().nextInt(1000));
				this.object = this.exchanger.exchange(this.object);
				System.out.println(Thread.currentThread().getName()
						+ " exchanged " + previous + " for " + this.object);
				for (int i = 0; i < 100; i++) {
					System.out.println(Thread.currentThread().getName()
							+ " exchanged " + previous + " for " + this.object
							+ "--->" + i);
					Thread.sleep(100);

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}