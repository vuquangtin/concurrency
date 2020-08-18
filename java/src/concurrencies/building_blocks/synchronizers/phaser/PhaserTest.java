package synchronizers.phaser;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class PhaserTest {

	@Test
	public void testBasics() {
		final Phaser phaser = new Phaser();
		/**
		 * Current phase is 0 because they're no arrive invocations before.
		 */
		assertTrue("Current phase should be 0 but is " + phaser.getPhase(),
				phaser.getPhase() == 0);

		/**
		 * This block will try a TimeoutException. Why ? Because the phase 0
		 * isn't advanced. It will be advanced when all registered tasks will
		 * invoke arrive* method.
		 */
		boolean wasTe = false;
		try {
			phaser.awaitAdvanceInterruptibly(0, 2, TimeUnit.SECONDS);
		} catch (TimeoutException | InterruptedException te) {
			wasTe = true;
		}
		assertTrue("TimeoutException should be thrown but it wasn't", wasTe);

		Thread worker1 = new Thread(new WorkerThread(phaser, "#1"));
		Thread worker2 = new Thread(new WorkerThread(phaser, "#2"));
		Thread worker3 = new Thread(new WorkerThread(phaser, "#3"));
		worker1.start();

		final Map<String, String> crashContainer = new HashMap<String, String>();
		/**
		 * Note that all phaser tasks call arrive* after 2 seconds. Here, we're
		 * waiting 3 seconds before to check if 0 phase was reached. This code
		 * shouldn't fail because phase 0 should be done before this timeout.
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					phaser.awaitAdvanceInterruptibly(0, 3, TimeUnit.SECONDS);
					crashContainer.put("testOk3Sec", "OK");
				} catch (Exception e) {
					crashContainer.put("testOk3Sec", "NOK");
				}
			}
		}).start();
		worker2.start();
		worker3.start();
		phaser.arriveAndAwaitAdvance();

		/**
		 * Because phaser can be reused, we can run once again our workers with
		 * the same Phaser instance. However, be careful about the number of
		 * registered tasks. If you register new tasks without unregistering the
		 * old ones (already terminated), your code will never stop. It's the
		 * reason why in this sample we invoke awaitAndDeregister in tasks run()
		 * method.
		 */
		Thread worker4 = new Thread(new WorkerThread(phaser, "#4"));
		Thread worker5 = new Thread(new WorkerThread(phaser, "#5"));
		Thread worker6 = new Thread(new WorkerThread(phaser, "#6"));
		worker4.start();
		worker5.start();
		worker6.start();

		/**
		 * We test again the execution of specific code after the terminate of
		 * the 1st phase.
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					phaser.awaitAdvanceInterruptibly(1, 3, TimeUnit.SECONDS);
					crashContainer.put("testOk3SecP1", "OK");
				} catch (Exception e) {
					crashContainer.put("testOk3SecP1", "NOK");
				}
			}
		}).start();
		phaser.arriveAndAwaitAdvance();

		// Sleep by security
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertTrue(
				"Phase 0: 3 seconds timeout method shouldn't fail but it was",
				crashContainer.get("testOk3Sec").equals("OK"));
		assertTrue(
				"Phase 1: 3 seconds timeout method shouldn't fail but it was",
				crashContainer.get("testOk3SecP1").equals("OK"));
	}
}

class WorkerThread implements Runnable {

	private Phaser phaser;
	private String name;

	public WorkerThread(Phaser phaser, String name) {
		this.phaser = phaser;
		this.name = name;
		phaser.register();
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.phaser.arriveAndDeregister();
	}

}