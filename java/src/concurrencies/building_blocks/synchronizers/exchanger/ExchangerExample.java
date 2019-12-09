package synchronizers.exchanger;

import java.util.concurrent.Exchanger;

public class ExchangerExample {

	Exchanger<String> exchanger = new Exchanger<String>();

	private class Producer implements Runnable {
		private String queue;

		@Override
		public void run() {
			try {
				// create tasks & fill the queue
				// exchange the full queue for a empty queue with Consumer
				queue = exchanger.exchange("Ready Queue");
				System.out.println(Thread.currentThread().getName() + " now has " + queue);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private class Consumer implements Runnable {

		private String queue;

		@Override
		public void run() {
			try {
				// do procesing & empty the queue
				// exchange the empty queue for a full queue with Producer
				queue = exchanger.exchange("Empty Queue");
				System.out.println(Thread.currentThread().getName() + " now has " + queue);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void start() {
		new Thread(new Producer(), "Producer").start();
		new Thread(new Consumer(), "Consumer").start();
		
	}

	public static void main(String[] args) {
		new ExchangerExample().start();
	}

}
