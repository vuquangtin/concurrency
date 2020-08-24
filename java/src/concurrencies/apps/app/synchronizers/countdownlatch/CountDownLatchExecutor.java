package app.synchronizers.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 
 * COUNTDOWNLATCH : fonctionne un peu comme un COMPTE A REBOURS pour les
 * threads. <li> <li>Il permet à des threads de patienter jusqu'à ce qu'un
 * compteur ait été ramené à 0. <li>On initialize CountDownLatch() avec une
 * valeur initiale du compte à rebours. <li> <li>await() : mets en attente le
 * thread courant qui sera libéré lorsque le compte sera à Zéro <li>countDown()
 * : décrémente le compte a rebours <li>getCount() : retourne la valeur actuelle
 * du compte a rebours
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CountDownLatchExecutor {

	static CountDownLatch countDownLatch = new CountDownLatch(10);

	public static void main(String[] args) {

		Thread t1 = new Thread(
				() -> {
					System.out.println("Premier thread en attente !..");
					try {
						countDownLatch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out
							.println("Premier thread libéré  après le compte à rebours");
				});

		Thread t2 = new Thread(
				() -> {
					System.out.println("Deuxième thread en attente !..");
					try {
						countDownLatch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out
							.println("Deuxième thread libéré  après le compte à rebours");
				});

		Fenetre fenetre = new Fenetre(countDownLatch);

		t1.start();
		t2.start();
	}
}