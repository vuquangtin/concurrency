package basic.thread1;

import java.util.concurrent.Exchanger;

public class ExchangerMain {
	public static void main(String[] args) {
		Exchanger exchanger = new Exchanger();

		ExchangerRunnable exchangerRunnable1 = new ExchangerRunnable(exchanger, "A");

		ExchangerRunnable exchangerRunnable2 = new ExchangerRunnable(exchanger, "B");

		new Thread(exchangerRunnable1).start();
		new Thread(exchangerRunnable2).start();
	}
}
