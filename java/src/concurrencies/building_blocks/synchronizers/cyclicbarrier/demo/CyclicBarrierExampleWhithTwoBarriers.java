package synchronizers.cyclicbarrier.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CyclicBarrierExampleWhithTwoBarriers {

	public static void main(String[] args) {

		Runnable barrier1Action = new Runnable() { // çàäà÷à, êîòîðàÿ áóäåò
													// âûïîë-
			// íÿòüñÿ, êîãäà ïîòîêè "âñòðåòÿòñÿ"
			public void run() {
				System.out.println("BarrierAction 1 executed ");
			}
		};
		Runnable barrier2Action = new Runnable() {
			public void run() {
				System.out.println("BarrierAction 2 executed ");
			}
		};

		/*
		 * îãðàíè÷èâàåì êîëè÷åñòâî ïîòîêîâ, êîòîðûå äîëæíû âñòðåòèòüñÿ, äâóìÿ.
		 * Òàêæå ïåðåäàåì îáúåêòó CyclicBarrier äåéñòâèå - barrier1Action,
		 * êîòîðîå äîëæíî ïðîèçîéòè, êîãäà ïîòîêè âñòðåòÿòñÿ
		 */
		CyclicBarrier barrier1 = new CyclicBarrier(2, barrier1Action);
		CyclicBarrier barrier2 = new CyclicBarrier(2, barrier2Action);

		CyclicBarrierRunnable barrierRunnable1 = new CyclicBarrierRunnable(
				barrier1, barrier2);

		CyclicBarrierRunnable barrierRunnable2 = new CyclicBarrierRunnable(
				barrier1, barrier2);

		new Thread(barrierRunnable1).start();
		new Thread(barrierRunnable2).start();
	}
}

class CyclicBarrierRunnable implements Runnable {

	CyclicBarrier barrier1 = null;
	CyclicBarrier barrier2 = null;

	public CyclicBarrierRunnable(CyclicBarrier barrier1, CyclicBarrier barrier2) {
		this.barrier1 = barrier1;
		this.barrier2 = barrier2;
	}

	public void run() {
		try {
			Thread.sleep(1000);
			System.out.println(Thread.currentThread().getName()
					+ " waiting at barrier 1");
			this.barrier1.await(); // ïîòîê äîñòèã áàðüåðà ¹ 1. Ïîòîê
									// áëîêèðóåòñÿ
			// è æäåò, ïîêà äðóãîé ïîòîê äîñòèãíåò áàðüåðà

			Thread.sleep(1000);
			System.out.println(Thread.currentThread().getName()
					+ " waiting at barrier 2");
			this.barrier2.await(); // ïîòîê äîñòèã áàðüåðà ¹ 2. Ïîòîê
									// áëîêèðóåòñÿ
			// è æäåò, ïîêà äðóãîé ïîòîê äîñòèãíåò áàðüåðà

			System.out.println(Thread.currentThread().getName() + " done!"); // äåéñòâèå,
																				// ïîñëå
																				// òîãî,
																				// êàê
																				// ïîòîêè
			// ïðîøëè áàðüåð

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}