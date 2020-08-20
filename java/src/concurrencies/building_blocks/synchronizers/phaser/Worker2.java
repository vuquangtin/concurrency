package synchronizers.phaser;


/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Worker2 implements Runnable
{
   private MyPhaser phaser;
   private int result = 0;

   public Worker2(MyPhaser phaser)
   {
      this.phaser = phaser;
      this.phaser.register();
   }

   @Override
   public void run()
   {
      result = 10 + (int)(Math.random()*100);
      Be.idleFor(result);
      int unArrived = phaser.getUnarrivedParties();
      System.out.println(Thread.currentThread().getName() + ", unarrived parts = " + unArrived + ", phase = " + phaser.getPhase());
      int phase = phaser.arrive();
      // entspricht countDown(); // blockiert nicht

      System.out.println(Thread.currentThread().getName() + " passed arrive, phase = " + phase);
      // Phase wird nicht hochgezählt

   }  // end run()

   public int getResult()
   {
      return result;
   }
}

