package synchronizers.exchanger;

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

public class DemoExchanger {

	public static void main(String[] args) {
		doDemo();
	}

	public static void doDemo() {

		Exchanger<String> exchanger = new Exchanger<String>();

		DemoExchangerRunnable exchangerRunnable1 = new DemoExchangerRunnable(
				exchanger, "A");
		DemoExchangerRunnable exchangerRunnable2 = new DemoExchangerRunnable(
				exchanger, "B");

		new Thread(exchangerRunnable1).start();
		new Thread(exchangerRunnable2).start();

	}
}

class DemoExchangerRunnable implements Runnable {

	private Exchanger<String> exchanger;
	private String data;

	public DemoExchangerRunnable(Exchanger<String> exchanger, String data) {
		this.exchanger = exchanger;
		this.data = data;
	}

	public void run() {
		while (true) {
			try {
				String previous = this.data;
				this.data = this.exchanger.exchange(this.data);
				System.out.printf("%s exchanged %s for %s\n", Thread
						.currentThread().getName(), previous, this.data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}