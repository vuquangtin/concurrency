package concurrency.java.optimize.test2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import concurrency.java.optimize.tasks.DemoTask;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CustomThreadPoolExecutor extends ThreadPoolExecutor {
    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t , Runnable r){
        super.beforeExecute(t,r);
    }
    @Override
    protected void afterExecute(Runnable r, Throwable t)
    {
        super.afterExecute(r, t);
        if (t != null)
        {
            t.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        Integer threadCounter = 0;
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(50);
        CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(10, 20, 5000, TimeUnit.MILLISECONDS, blockingQueue);
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler()
        {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
            {
                System.out.println("DemoTask Rejected : " + ((DemoTask) r).getName());
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("Lets add another time : " + ((DemoTask) r).getName());
                executor.execute(r);
            }
        });
        // Let start all core threads initially
        executor.prestartAllCoreThreads();
        while (true)
        {
            threadCounter++;
            // Adding threads one by one
            //System.out.println("Adding DemoTask : " + threadCounter);
            executor.execute(new DemoTask(threadCounter.toString()));
            if (threadCounter == 1000)
                break;
        }
    }
}

