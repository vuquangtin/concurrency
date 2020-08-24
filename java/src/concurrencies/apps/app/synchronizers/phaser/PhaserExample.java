package app.synchronizers.phaser;

import java.time.LocalTime;
import java.util.concurrent.Phaser;

/**
 * https://www.logicbig.com/tutorials/core-java-tutorial/java-multi-threading/phaser.html
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class PhaserExample {
	  private static final Phaser phaser = new Phaser();

	  public static void main(String[] args) throws InterruptedException {
	      startTask(0);
	      startTask(500);
	      startTask(1000);
	  }

	  private static void startTask(long initialDelay) throws InterruptedException {
	      Thread.sleep(initialDelay);
	      new Thread(PhaserExample::taskRun).start();
	  }

	  private static void taskRun() {
	      phaser.register();//registering this thread
	      print("after registering");
	      for (int i = 1; i <= 2; i++) {
	          try {
	              //doing some work
	              Thread.sleep(3000);
	          } catch (InterruptedException e) {
	              e.printStackTrace();
	          }
	          //the barrier point
	          print("before arrive " + i);
	          phaser.arriveAndAwaitAdvance();//current thread will wait for others to arrive
	          print("after arrive " + i);
	      }
	  }

	  private static void print(String msg) {
	      System.out.printf("%-20s: %10s, t=%s, registered=%s, arrived=%s, unarrived=%s phase=%s%n",
	              msg,
	              Thread.currentThread().getName(),
	              LocalTime.now(),
	              phaser.getRegisteredParties(),
	              phaser.getArrivedParties(),
	              phaser.getUnarrivedParties(),
	              phaser.getPhase()
	      );
	  }
	}