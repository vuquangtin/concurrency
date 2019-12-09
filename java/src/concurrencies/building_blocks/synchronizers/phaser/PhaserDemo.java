package synchronizers.phaser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
class LongRunningAction implements Runnable {
	private String threadName;
	private Phaser ph;

	LongRunningAction(String threadName, Phaser ph) {
		this.threadName = threadName;
		this.ph = ph;
		ph.register();
	}

	@Override
	public void run() {
		ph.arriveAndAwaitAdvance();
		try {
			Thread.sleep(20);
			System.out.println("go done");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ph.arriveAndDeregister();
	}
}

public class PhaserDemo {
	public static void main(String[] args) {
		// given
		ExecutorService executorService = Executors.newCachedThreadPool();
		Phaser ph = new Phaser(1);
		System.out.println(ph.getPhase());

		// when
		executorService.submit(new LongRunningAction("thread-1", ph));
		executorService.submit(new LongRunningAction("thread-2", ph));
		executorService.submit(new LongRunningAction("thread-3", ph));

		// then
		ph.arriveAndAwaitAdvance();
		System.out.println(ph.getPhase());

		// and
		executorService.submit(new LongRunningAction("thread-4", ph));
		executorService.submit(new LongRunningAction("thread-5", ph));
		ph.arriveAndAwaitAdvance();
		System.out.println(ph.getPhase());

		ph.arriveAndDeregister();
	}
}