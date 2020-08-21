package synchronizers.phaser.demo;

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
public class PhaserTestDemo {

	public static void main(String args[]) {
		Phaser phsr = new Phaser(1);
		int curPhase;

		System.out.println("Запуск потоков");

		new MyThread(phsr, "A");
		new MyThread(phsr, "B");
		new MyThread(phsr, "C");

		// ожидать заверешния всеми потоками исполнения первой фазы
		curPhase = phsr.getPhase();

		System.out.println(" main: ArrivedParties:" + phsr.getArrivedParties()
				+ " UnarrivedParties:" + phsr.getUnarrivedParties()
				+ " RegisteredParties:" + phsr.getRegisteredParties()
				+ " Phase:" + phsr.getPhase());

		phsr.arriveAndAwaitAdvance();
		System.out.println("Фаза " + curPhase + " завершена");

		// ожидать завершения всеми потоками исполнения второй фазы
		curPhase = phsr.getPhase();
		phsr.arriveAndAwaitAdvance();
		System.out.println("Фаза " + curPhase + " завершена");

		curPhase = phsr.getPhase();
		phsr.arriveAndAwaitAdvance();
		System.out.println("Фаза " + curPhase + " завершена");

		// снять основной поток исполнения с регистрации
		phsr.arriveAndDeregister();

		if (phsr.isTerminated()) {
			System.out.println("Синхронизатор фаз завершен");
		}
	}
}

// Поток исполнения, использующий синхронизатор фаз типа Phaser
class MyThread implements Runnable {
	Phaser phsr;
	String name;

	MyThread(Phaser p, String n) {
		phsr = p;
		name = n;
		phsr.register();
		new Thread(this).start();
	}

	public void run() {

		System.out.println("Поток " + name + " начинает первую фазу");

		System.out.println(" run: ArrivedParties:" + phsr.getArrivedParties()
				+ " UnarrivedParties:" + phsr.getUnarrivedParties()
				+ " RegisteredParties:" + phsr.getRegisteredParties()
				+ " Phase:" + phsr.getPhase());

		phsr.arriveAndAwaitAdvance(); // известить о достижении фазы

		// Небольшая пауза, чтобы не нарушить порядок вывода.
		// Только для иллюстрации, но необязательно для правильного
		// функционирования синхронизатора фаз

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			System.out.println(e);
		}

		System.out.println("Поток " + name + " начинает вторую фазу");
		phsr.arriveAndAwaitAdvance(); // известить о достижении фазы

		// Небольшая пауза, чтобы не нарушить порядок вывода.
		// Только для иллюстрации, но необязательно для правильного
		// функционирования синхронизатора фаз

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			System.out.println(e);
		}

		System.out.println("Поток " + name + " начинает третью фазу");

		// известить о достижении фазы и снять потоки с регистрации
		phsr.arriveAndDeregister();
	}
}