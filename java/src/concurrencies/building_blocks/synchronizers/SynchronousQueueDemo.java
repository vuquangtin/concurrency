package synchronizers;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {

	public static void main(String[] args) {
		final SynchronousQueue<String> queue = new SynchronousQueue<String>();

		// start publisher thread
		new Thread(new Runnable() {

			@Override
			public void run() {
				String event = "SYNCHRONOUS_EVENT";
				String another_event = "ANOTHER_EVENT";
				try {
					queue.put(event);
					System.out.printf("[%s] published event : %s %n", Thread.currentThread().getName(), event);

					queue.put(another_event);
					System.out.printf("[%s] published event : %s %n", Thread.currentThread().getName(), another_event);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		// start consumer thread
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String event = queue.take();
					// thread will block here
					System.out.printf("[%s] consumed event : %s %n", Thread.currentThread().getName(), event);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}