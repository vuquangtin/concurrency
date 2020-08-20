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
public class PhaserTest6 {
	public static void main(String[] args) {
		Phaser phaser = new Phaser(3) {
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.println("------------------------����ҷָ��:phase="
						+ phase + ";registeredParties=" + registeredParties
						+ "------------------------");
				return registeredParties == 1;
			}
		};

		for (int i = 0; i < 3; i++) {
			Thread thread = new Thread(new Task06(phaser, i), "PhaserTest" + i);
			thread.start();
		}

		do {
			phaser.arriveAndAwaitAdvance(); // ���̵߳ȴ��������������..
		} while (!phaser.isTerminated());

		System.out.println("����ִ�����.....");
	}

	static class Task06 implements Runnable {
		private final Phaser phaser;
		private int i;

		Task06(Phaser phaser, int i) {
			this.phaser = phaser;
			this.i = i;
		}

		@Override
		public void run() {
			System.out.println("----ִ��������:"
					+ Thread.currentThread().getName() + "; i = " + i);
			// ���������
			while (true) {
				i++;
				if (i == 5) { // ��i == 5ʱ�����������Phaser�����߳� - 1
					phaser.arriveAndAwaitAdvance();
					break;
				} else {
					// �ȴ������̵߳���׶��յ㣬��һ�������һ���׶�
					System.out.println(Thread.currentThread().getName()
							+ "; i = " + i);
					phaser.arriveAndAwaitAdvance();
				}
			}
		}

	}
}