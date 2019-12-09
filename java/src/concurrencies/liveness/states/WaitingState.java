package states;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class WaitingState implements Runnable {
	public static Thread t1;

	public static void main(String[] args) {
		t1 = new Thread(new WaitingState());
		t1.start();
	}

	public void run() {
		Thread t2 = new Thread(new DemoThreadWS());
		t2.start();

		try {
			t2.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("Thread interrupted");
		}
	}
}

class DemoThreadWS implements Runnable {
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.err.println("Thread interrupted");
		}

		System.out.println(WaitingState.t1.getState());
	}
}