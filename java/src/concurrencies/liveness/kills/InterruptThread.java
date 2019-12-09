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
public class InterruptThread implements Runnable {

	private Thread worker;
	private AtomicBoolean running = new AtomicBoolean(false);
	private int interval;

	public void start(int interval) {
		this.interval=interval;
		worker = new Thread(this);
		worker.start();
	}

	public void interrupt() {
		running.set(false);
		worker.interrupt();
	}

	boolean isRunning() {
		return running.get();
	}

	boolean isStopped() {
		return running.get();
	}

	public void run() {
		running.set(true);
		while (running.get()) {
			try {
				System.out.println("running");
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out
						.println("Thread was interrupted, Failed to complete operation");
			}
			
			// do something
		}
		System.out.println("end");
	}
	public static void main(String[] args) {
		InterruptThread t=new InterruptThread();
		t.start(1000);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.interrupt();
	}
}