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
public class PhaserTest3 {
	public static void main(String[] args) {
		Phaser phaser = new Phaser(3){
			/**
			 * registeredParties:�߳�ע�������
			 * phase:����÷������߳���
			 */
			 protected boolean onAdvance(int phase, int registeredParties) { 
				 System.out.println(Thread.currentThread().getName() + "ִ��onAdvance����.....;phase:" + phase + "registeredParties=" + registeredParties);
				 return phase == 2; 
			 }
		};
		
		for(int i = 0 ; i < 3 ; i++){
			Task03 task = new Task03(phaser);
			Thread thread = new Thread(task,"task" + i);
			thread.start();
		}
		while(!phaser.isTerminated()){
			phaser.arriveAndAwaitAdvance();	//���߳�һֱ�ȴ�
		}
		System.out.println("���߳������Ѿ�����....");
	}
	
	static class Task03 implements Runnable{
		private final Phaser phaser;
		
		public Task03(Phaser phaser){
			this.phaser = phaser;
		}
		
		@Override
		public void run() {
			do{
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "��ʼִ������...");
				phaser.arriveAndAwaitAdvance();
			}while(!phaser.isTerminated());
		}
	}
}