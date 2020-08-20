package synchronizers.phaser;

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
class MyThreadZero extends Thread {
	Phaser phaser;

	public MyThreadZero(Phaser phaser) {
		this.phaser = phaser;
	}

	public void run() {
		phaser.register();

	}
}

class MyThreadTwo extends Thread {
	Phaser phaser;

	public MyThreadTwo(Phaser phaser) {
		this.phaser = phaser;
	}

	public void run() {
		phaser.arriveAndAwaitAdvance();
		System.out.println("The thread is unregistered");
	}
}

class MyThreadThree extends Thread {
	Phaser phaser;

	public MyThreadThree(Phaser phaser) {
		this.phaser = phaser;
	}

	public void run() {
		phaser.arrive();
		System.out.println("The thread is unregistered");
	}
}

public class MePhaser {
	public static void main(String[] args) {
		Phaser phaser = new Phaser(3);

		MyThreadZero myThread = new MyThreadZero(phaser);

		MyThreadTwo threadTwo1 = new MyThreadTwo(phaser);
		MyThreadTwo threadTwo2 = new MyThreadTwo(phaser);
		MyThreadTwo threadTwo3 = new MyThreadTwo(phaser);
		MyThreadTwo threadTwo4 = new MyThreadTwo(phaser);

		MyThreadThree threadThree = new MyThreadThree(phaser);

		myThread.start();

		threadTwo1.start();
		threadTwo2.start();
		threadTwo3.start();
		//threadTwo4.start();
		threadThree.start();
	}
}