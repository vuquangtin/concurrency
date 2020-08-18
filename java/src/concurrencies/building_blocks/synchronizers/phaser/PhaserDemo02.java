package synchronizers.phaser;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class PhaserDemo02 {
	public static void main(String[] args) {
		MyPhaser phaser = new MyPhaser();
		int workerCount = 5;

		ArrayList<Worker2> workers = new ArrayList<>();
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < workerCount; i++) {
			Worker2 worker = new Worker2(phaser);
			// alle workers registrieren sich und rufen phaser.arrive() !
			workers.add(worker);
			executorService.execute(worker);
		}

		System.out.println("main begins waiting, phase = " + phaser.getPhase());
		int phase = phaser.awaitAdvance(0); // blockiert und liefert 1
		System.out.println("main: end waiting, phase = " + phase);
		System.out.print("Collecting results from workers:  ");
		for (Worker2 worker : workers) {
			System.out.print(worker.getResult() + "  ");
		}
		System.out.println();

		System.out.println("end main");
		executorService.shutdown();
	}
}