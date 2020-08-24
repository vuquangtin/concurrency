package app.synchronizers.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import javax.swing.JProgressBar;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Download extends Thread {

	private final JProgressBar balken;
	private CyclicBarrier barrier;
	private CountDownLatch lach;

	// weitere Attribute zur Synchronisation hier definieren

	public Download(JProgressBar balken, CyclicBarrier barrier,
			CountDownLatch lach/* ggf. weitere Objekte */) {
		this.balken = balken;
		this.barrier = barrier;
		this.lach = lach;
		// ...
	}

	public void run() {
		int x = 0;
		int zeit = new Random().nextInt(1000);
		try {
			barrier.await();
			Thread.sleep(zeit);
			while (balken.getValue() != balken.getMaximum()) {
				Thread.sleep(zeit);
				balken.setValue(x++);
			}

			lach.countDown();
			lach.await();
			System.out.println("the download ended!");

		} catch (InterruptedException | BrokenBarrierException e) {
		}
	}
	/*
	 * hier die Methode definieren, die jeweils den Balken weiterschiebt Mit
	 * balken.getMaximum() bekommt man den Wert fuer 100 % gefuellt Mit
	 * balken.setValue(...) kann man den Balken einstellen (wieviel gefuellt)
	 * dargestellt wird Setzen Sie den value jeweils und legen Sie die Methode
	 * dann für eine zufaellige Zeit schlafen
	 */
}
