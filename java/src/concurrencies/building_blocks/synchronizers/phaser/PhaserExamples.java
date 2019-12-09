package synchronizers.phaser;

import java.util.concurrent.Phaser;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class PhaserExamples {  
	 
	   public static void main(String[] args) {
	 
	      Phaser phaser = new Phaser();
	 
	      Thread t1 = new MyThread(phaser,1000);
	      Thread t2 = new MyThread(phaser,3000);
	      Thread t3 = new MyThread(phaser,10000);
	      t1.start();
	      t2.start();
	      t3.start();
	 
	   }
	}
	 
	class MyThread extends Thread {
	 
	   Phaser phaser;
	   int sleep;
	 
	   MyThread(Phaser phaser, int sleep){
	      this.phaser=phaser;
	      this.sleep=sleep;
	   }
	 
	   public void run() {
	      phaser.register();
	      System.out.println(this.getName() + " begin");
	      try {
	         Thread.sleep(sleep);
	      } catch (Exception e) { 
	      }
	      phaser.arriveAndAwaitAdvance();
	      System.out.println(this.getName() + " middle");
	      try {
	         Thread.sleep(sleep);
	      } catch (Exception e) { 
	      }
	      System.out.println(this.getName() + " end");
	   }
	}