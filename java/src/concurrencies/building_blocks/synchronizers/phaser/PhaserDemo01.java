package synchronizers.phaser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class PhaserDemo01 {
	public static void main(String[] args) {
		Phaser phaser = new MyPhaser();
		int workerCount = 5;

		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < workerCount; i++) {
			Worker worker = new Worker(phaser);
			// alle workers warten mit awaitAdvance(0)
			worker.setName("Worker-" + (i + 1));
			executorService.execute(worker);
		}

		Be.idleFor(200);
		System.out.println("main registers to phaser");
		phaser.register();
		System.out.println("main calls arrive");
		phaser.arrive();
		System.out.println("end main");
		executorService.shutdown();
	}
}