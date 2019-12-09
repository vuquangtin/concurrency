package joinkeyword;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class SampleThread extends Thread {
	public int processingCount = 0;

	SampleThread(int processingCount) {
		this.processingCount = processingCount;
		System.out.println("Thread Created");
	}

	@Override
	public void run() {
		System.out.println("Thread " + this.getName() + " started");
		while (processingCount > 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Thread " + this.getName() + " interrupted");
			}
			processingCount--;
		}
		System.out.println("Thread " + this.getName() + " exiting");
	}
}