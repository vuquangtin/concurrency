package learn.threads.vn.docs2;

import java.util.Vector;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Producer extends Thread {
	static final int MAXQUEUE = 5;
	private Vector messages = new Vector();

	public void run() {
		try {
			while (true) {
				putMessage();
				sleep(1000);
			}
		} catch (InterruptedException e) {
		}
	}

	private synchronized void putMessage() throws InterruptedException {

		while (messages.size() == MAXQUEUE)
			wait();
		messages.addElement(new java.util.Date().toString());
		notify();
	}

	// Called by Consumer
	public synchronized String getMessage() throws InterruptedException {
		notify();
		while (messages.size() == 0)
			wait();
		String message = (String) messages.firstElement();
		messages.removeElement(message);
		return message;
	}
}

class Consumer extends Thread {
	Producer producer;

	Consumer(Producer p) {
		producer = p;
	}

	public void run() {
		try {
			while (true) {
				String message = producer.getMessage();
				System.out.println("Got message: " + message);
				sleep(2000);
			}
		} catch (InterruptedException e) {
		}
	}

	public static void main(String args[]) {
		Producer producer = new Producer();
		producer.start();
		new Consumer(producer).start();
	}
}