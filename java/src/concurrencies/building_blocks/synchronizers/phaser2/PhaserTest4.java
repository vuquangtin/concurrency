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
public class PhaserTest4 {
	public static void main(String[] args) {
		final int phaseToTerminate = 3;
		Phaser phaser = new Phaser(5) {
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.println("执行onAdvance方法.....");
				return phase >= phaseToTerminate || registeredParties == 0;
			}
		};

		for (int i = 0; i < 5; i++) {
			Task_04 task = new Task_04(phaser);
			Thread thread = new Thread(task, "task_" + i);
			thread.start();
		}

		phaser.register();
		while (!phaser.isTerminated()) {
			phaser.arriveAndAwaitAdvance();
		}
		;

		System.out.println("主线程任务已经结束....");
	}

	static class Task_04 implements Runnable {
		private final Phaser phaser;

		public Task_04(Phaser phaser) {
			this.phaser = phaser;
		}

		@Override
		public void run() {
			do {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()
						+ "开始执行任务...");
				phaser.arriveAndAwaitAdvance();
			} while (!phaser.isTerminated());
		}

	}
}
