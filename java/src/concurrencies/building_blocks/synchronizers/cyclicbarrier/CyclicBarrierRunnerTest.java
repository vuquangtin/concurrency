package synchronizers.cyclicbarrier;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class CyclicBarrierRunnerTest {

	public static void main(String[] args) {
		AtomicInteger integer = new AtomicInteger(0);
		CyclicBarrierRunner runner = new CyclicBarrierRunner(1,
				TimeUnit.SECONDS);
		// submit 3 jobs
		runner.submit(integer::incrementAndGet);
		runner.submit(integer::incrementAndGet);
		runner.submit(integer::incrementAndGet);		
		runner.execute();
	}

	public void testCyclicBarrierRunnerWithError() {
		CyclicBarrierRunner runner = new CyclicBarrierRunner(10, TimeUnit.DAYS);
		runner.submit(() -> {
			try {
				// This job will sleep for basically forever, but the other
				// job's exception will cause
				// the whole barrier runner to fail immediately.
				TimeUnit.DAYS.sleep(10);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		runner.submit(() -> {
			throw new RuntimeException();
		});
		runner.execute();
	}
}