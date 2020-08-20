package synchronizers.phaser2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class PhaserTest2 {
	public static void main(String[] args) throws InterruptedException, IOException {
		Phaser phaser = new Phaser(6);
		int i = 0;
		for(i = 1 ; i <= 6 ; i++){
			Task02 task = new Task02(phaser);
			Thread thread = new Thread(task,"task" + i);
			thread.start();
		}
		System.out.println("Press ENTER to continue");  
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
        reader.readLine();  
        //���»س���ִ��
        phaser.arriveAndDeregister(); 
	}
	
	
	static class Task02 implements Runnable{
		private final Phaser phaser;
		
		public Task02(Phaser phaser){
			this.phaser = phaser;
		}
		
		
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + "��ʼִ������...");
			phaser.arriveAndAwaitAdvance();
			System.out.println(Thread.currentThread().getName() + "����ִ������...");
		}
	}
}