package basic.all;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Pattern: Protected Shared State
 * 
 * Example: A simple Counter example.
 */
@ThreadSafe
public class VisitCounter {

	@GuardedBy("this")
	private int value;

	public synchronized int actualValue() {
		return value;
	}

	public synchronized void increase() {
		value++;
	}

	public synchronized void decrease() {
		value--;
	}

	public static void main(String[] args) {
		VisitCounter counter = new VisitCounter();
		ExecutorService threadPool = Executors.newCachedThreadPool();
		for (int i = 1; i <= 50; i++) {
			System.out.println("value " + counter.actualValue() + " i " + i);
			threadPool.execute(() -> counter.increase());
		}
		threadPool.shutdown();
		System.out.println(counter.actualValue());
	}
}
