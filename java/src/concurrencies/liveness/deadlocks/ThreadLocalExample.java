package deadlocks;
/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class ThreadLocalExample implements Runnable {
	private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
	private final int value;

	public ThreadLocalExample(int value) {
		this.value = value;
	}

	@Override
	public void run() {
		threadLocal.set(value);
		Integer integer = threadLocal.get();
		System.out.println("[" + Thread.currentThread().getName() + "]: " + integer);
	}

	public static void main(String[] args) throws InterruptedException {
		Thread threads[] = new Thread[5];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new ThreadLocalExample(i), "thread-" + i);
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
		}
	}
}