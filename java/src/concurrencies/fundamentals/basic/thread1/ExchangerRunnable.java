package basic.thread1;

import java.util.concurrent.Exchanger;

public class ExchangerRunnable implements Runnable {

	Exchanger<Object> exchanger = null;
	Object object = null;

	public ExchangerRunnable(Exchanger<Object> exchanger, Object object) {
		this.exchanger = exchanger;
		this.object = object;
	}

	public void run() {
		int m = 0;

		while (true) {
			try {
				Object previous = this.object;

				this.object = this.exchanger.exchange(this.object);

				System.out.println(Thread.currentThread().getName() + " exchanged " + previous + " for " + this.object);
				m++;
				//Thread.sleep(2000);
				if (m > 10)
					break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
