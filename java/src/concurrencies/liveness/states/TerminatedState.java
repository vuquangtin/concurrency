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
public class TerminatedState implements Runnable {
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new TerminatedState());
		t1.start();
		// The following sleep method will give enough time for
		// thread t1 to complete
		Thread.sleep(1000);
		System.out.println(t1.getState());
	}

	@Override
	public void run() {
		// No processing in this block
	}
}