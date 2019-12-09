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
public class TimedWaitingState {
	public static void main(String[] args) throws InterruptedException {
		DemoThread obj1 = new DemoThread();
		Thread t1 = new Thread(obj1);
		t1.start();

		// The following sleep will give enough time for ThreadScheduler
		// to start processing of thread t1
		Thread.sleep(1000);
		System.out.println(t1.getState());
	}
}

class DemoThread implements Runnable {
	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("Thread interrupted");
		}
	}
}