package concurrency.java.optimize.executor.test;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import concurrency.java.optimize.executor.BoundedStrategy;
import concurrency.java.optimize.executor.KeyRunnable;
import concurrency.java.optimize.executor.KeySequentialBoundedExecutor;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class KeySequentialBoundedExecutorTest {

	@Test(timeout = 5000)
	public void underLoad() throws InterruptedException {
		ExecutorService underlyingExecutor = Executors.newFixedThreadPool(10);
		KeySequentialBoundedExecutor boundedExecutor = new KeySequentialBoundedExecutor(5, BoundedStrategy.BLOCK,
				underlyingExecutor);
		List<Integer> processed = Collections.synchronizedList(new LinkedList<>());

		for (int i = 0; i < 1000; ++i) {
			final int toProcess = i;
			boundedExecutor.execute(new KeyRunnable<>(i % 2, () -> processed.add(toProcess)));
		}

		boundedExecutor.drain(Long.MAX_VALUE, TimeUnit.SECONDS);
		underlyingExecutor.shutdownNow();

		int previousOdd = -1;
		int previousEven = -2;
		for (int p : processed) {
			if (p % 2 == 0) {
				assertEquals(previousEven + 2, p);
				previousEven = p;
			} else {
				assertEquals(previousOdd + 2, p);
				previousOdd = p;
			}
		}
	}
}