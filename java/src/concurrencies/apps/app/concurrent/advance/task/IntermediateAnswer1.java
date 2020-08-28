package app.concurrent.advance.task;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public final class IntermediateAnswer1 implements Runnable {
	private int count = 0;

	@Override
	public synchronized void run() {
		try {
			work();
		} catch (Exception e) {
			return;
		}
	}

	public void work() throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			count++;
			System.out.println("value:" + count);
			if (Thread.interrupted()) {
				System.out.println("Thread Interrupted at----------------"
						+ count);
				throw new InterruptedException();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		IntermediateAnswer1 c = new IntermediateAnswer1();
		Thread thread = new Thread(c);
		thread.start();
		Thread.sleep(1);
		thread.interrupt();
	}
}