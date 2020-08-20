package synchronizers.phaser2;

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

public class PhaserTest1 {
	public static void main(String[] args) {
		Phaser phaser = new Phaser(5);

		for (int i = 0; i < 5; i++) {
			Task01 task01 = new Task01(phaser);
			Thread thread = new Thread(task01, "PhaseTest" + i);
			thread.start();
		}
	}

	static class Task01 implements Runnable {
		private final Phaser phaser;

		public Task01(Phaser phaser) {
			this.phaser = phaser;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName()
					+ "ִ��������ɣ��ȴ���������ִ��......");
			// �ȴ���������ִ�����
			phaser.arriveAndAwaitAdvance();
			System.out.println(Thread.currentThread().getName()
					+ "����ִ������...");
		}
	}
}