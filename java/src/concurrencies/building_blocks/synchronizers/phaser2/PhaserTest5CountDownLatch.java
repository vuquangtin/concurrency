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
public class PhaserTest5CountDownLatch {
	public static void main(String[] args) {
		Phaser phaser = new Phaser(1);		//�൱��CountDownLatch(1) 
		
		//���������
		for(int i = 0 ; i < 3 ; i++){
			Task05 task = new Task05(phaser);
			Thread thread = new Thread(task,"PhaseTest" + i);
			thread.start();
		}
		
		try {
			//�ȴ�3��
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		phaser.arrive();		//countDownLatch.countDown()
	}
	
	static class Task05 implements Runnable{
		private final Phaser phaser;
		
		Task05(Phaser phaser){
			this.phaser = phaser;
		}
		
		@Override
		public void run() {
			phaser.awaitAdvance(phaser.getPhase());		//countDownLatch.await()
			System.out.println(Thread.currentThread().getName() + "ִ������...");
		}
	}
}