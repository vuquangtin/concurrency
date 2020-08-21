package synchronizers.cyclicbarrier.demo;

import java.util.concurrent.CyclicBarrier;

/**
 * 
 * http://tutorials.jenkov.com/java-util-concurrent/cyclicbarrier.html
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CyclicBarrierExample {
	private static final CyclicBarrier BARRIER = new CyclicBarrier(3,
			new FerryBoat());

	// Èíèöèàëèçèðóåì áàðüåð íà òðè ïîòîêà è òàñêîì, êîòîðûé áóäåò âûïîëíÿòüñÿ,
	// êîãäà
	// ó áàðüåðà ñîáåðåòñÿ òðè ïîòîêà. Ïîñëå ýòîãî, îíè áóäóò îñâîáîæäåíû.

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 9; i++) {
			new Thread(new Car(i)).start();
			Thread.sleep(400);
		}
	}

	// Òàñê, êîòîðûé áóäåò âûïîëíÿòüñÿ ïðè äîñòèæåíèè ñòîðîíàìè áàðüåðà
	public static class FerryBoat implements Runnable {
		@Override
		public void run() {
			try {
				Thread.sleep(500);
				System.out.println("Ïàðîì ïåðåïðàâèë àâòîìîáèëè!");
			} catch (InterruptedException e) {
			}
		}
	}

	// Ñòîðîíû, êîòîðûå áóäóò äîñòèãàòü áàðüåðà
	public static class Car implements Runnable {
		private int carNumber;

		public Car(int carNumber) {
			this.carNumber = carNumber;
		}

		@Override
		public void run() {
			try {
				System.out.printf(
						"Àâòîìîáèëü ¹%d ïîäúåõàë ê ïàðîìíîé ïåðåïðàâå.\n",
						carNumber);
				// Äëÿ óêàçàíèÿ ïîòîêó î òîì ÷òî îí äîñòèã áàðüåðà, íóæíî
				// âûçâàòü ìåòîä await()
				// Ïîñëå ýòîãî äàííûé ïîòîê áëîêèðóåòñÿ, è æäåò ïîêà îñòàëüíûå
				// ñòîðîíû äîñòèãíóò áàðüåðà
				BARRIER.await();
				System.out.printf("Àâòîìîáèëü ¹%d ïðîäîëæèë äâèæåíèå.\n",
						carNumber);
			} catch (Exception e) {
			}
		}
	}
}