package concurrency.java.executors;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadPool
{

   private static LimitedQueue<Runnable> queue = new LimitedQueue<Runnable>(1024);
   private static ThreadPoolExecutor pool;
   private static final boolean killDebuggers = true;
   private static int threadNum = 0;

   static
   {
		pool = new ThreadPoolExecutor(8, 8, 10L, TimeUnit.SECONDS,  queue);
      pool.setThreadFactory(getThreadFactory());
   }

   static public void submitTask(Runnable task)
   {
      
      if(killDebuggers)
      {
         Thread worker = new Thread(task, "Worker-"+threadNum++);
         worker.setPriority(Thread.MIN_PRIORITY);
         worker.setDaemon(true);
         worker.start();
      } else
      {
         pool.submit(task);
      }
   }

   private static ThreadFactory getThreadFactory()
   {
      return new LowPriThreadFactory();
   }

   public static class LimitedQueue<E> extends LinkedBlockingQueue<E> 
   {

      private static final long serialVersionUID = 1L;

      public LimitedQueue(int maxSize)
      {
         super(maxSize);
      }

      @Override
      public boolean offer(E e)
      {
         // turn offer() and add() into a blocking calls (unless interrupted)
         try {
            put(e);
            return true;
         } catch(InterruptedException ie) {
            Thread.currentThread().interrupt();
         }
         return false;
      }

   }

   public static class LowPriThreadFactory implements ThreadFactory
   {

      static private int nextThreadId = 0;
      static private ThreadGroup group = new ThreadGroup("low-priority");

      static
      {
         group.setDaemon(true);
         group.setMaxPriority(Thread.MIN_PRIORITY);;
      }


      @Override
      public Thread newThread(Runnable r)
      {
         Thread thread = new Thread(group,r, "low-priority-thread-"+(nextThreadId++));
         return thread;
      }

   }
}