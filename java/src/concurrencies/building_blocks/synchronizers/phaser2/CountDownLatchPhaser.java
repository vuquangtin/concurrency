package synchronizers.phaser2;

import java.text.SimpleDateFormat;
import java.util.Date;
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
public class CountDownLatchPhaser {
	final static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) throws InterruptedException {
		Phaser phaser = new Phaser(2);// CountDownLatch latch = new
										// CountDownLatch(2);//两个工人的协作
		Worker worker1 = new Worker("zhang san", 5000, phaser);
		Worker worker2 = new Worker("li si", 8000, phaser);
		worker1.start();//
		worker2.start();//
		phaser.awaitAdvance(phaser.getPhase()); // latch.await();//等待所有工人完成工作
		System.out.println("all work done at " + sdf.format(new Date()));
	}

	static class Worker extends Thread {
		String workerName;
		int workTime;
		Phaser phaser;

		public Worker(String workerName, int workTime, Phaser phaser) {
			this.workerName = workerName;
			this.workTime = workTime;
			this.phaser = phaser;
		}

		public void run() {
			System.out.println("Worker " + workerName + " do work begin at "
					+ sdf.format(new Date()));
			doWork();// 工作了
			System.out.println("Worker " + workerName + " do work complete at "
					+ sdf.format(new Date()));
			phaser.arrive();// latch.countDown();//工人完成工作，计数器减一,要放在finally里

		}

		private void doWork() {
			try {
				Thread.sleep(workTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}