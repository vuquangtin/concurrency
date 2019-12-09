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
public class BlockedState {
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new DemoThreadB());
		Thread t2 = new Thread(new DemoThreadB());

		t1.start();
		t2.start();

		Thread.sleep(1000);
		System.out.println(t2.getState());

		System.exit(0);
	}
}

class DemoThreadB implements Runnable {
	@Override
	public void run() {
		commonResource();
	}

	public static synchronized void commonResource() {
		while (true) {
			// Infinite loop to mimic heavy processing
			// 't1' won't leave this method
			// when 't2' try to enters this
		}
	}
}