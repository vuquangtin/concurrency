package kills;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class ControlSubThread implements Runnable {

	private Thread worker;
	private final AtomicBoolean running = new AtomicBoolean(false);
	private int interval;

	public ControlSubThread(int sleepInterval) {
		interval = sleepInterval;
	}

	public void start() {
		worker = new Thread(this);
		worker.start();
	}

	public void stop() {
		running.set(false);
	}

	public void run() {
		running.set(true);
		while (running.get()) {
			try {
				Thread.sleep(interval);
				System.out.println("running");
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out
						.println("Thread was interrupted, Failed to complete operation");
			}
			// do something here
		}
		System.out.println("end");
	}

	public static void main(String[] args) {
		ControlSubThread t = new ControlSubThread(1000);
		t.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.stop();

	}
}