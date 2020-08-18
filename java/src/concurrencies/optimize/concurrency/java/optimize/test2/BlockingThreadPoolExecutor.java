package concurrency.java.optimize.test2;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import concurrency.java.optimize.tasks.MyRunnable;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */




public class BlockingThreadPoolExecutor extends ThreadPoolExecutor {
    private final Semaphore semaphore;

    public BlockingThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue)
    {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        semaphore = new Semaphore(corePoolSize + 50);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r)
    {
        super.beforeExecute(t, r);
    }

    @Override
    public void execute(final Runnable task)
    {
        boolean acquired = false;
        do
        {
            try
            {
                semaphore.acquire();
                acquired = true;
            } catch (final InterruptedException e)
            {
                //LOGGER.warn("InterruptedException whilst aquiring semaphore", e);
            }
        } while (!acquired);
        try
        {
            super.execute(task);
        } catch (final RejectedExecutionException e)
        {
            System.out.println("Task Rejected");
            semaphore.release();
            throw e;
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t)
    {
        super.afterExecute(r, t);
        if (t != null)
        {
            t.printStackTrace();
        }
        semaphore.release();
    }

    public static void main(String[] args)
    {
        Integer threadCounter = 0;
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(50);
        BlockingThreadPoolExecutor executor = new BlockingThreadPoolExecutor(10, 20, 5000, TimeUnit.MILLISECONDS, blockingQueue);
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler()
        {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
            {
                System.out.println("DemoTask Rejected : " + ((MyRunnable) r).getName());
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("Lets add another time : " + ((MyRunnable) r).getName());
                executor.execute(r);
            }
        });
        // Let start all core threads initially
        executor.prestartAllCoreThreads();
        while (true)
        {
            threadCounter++;
            // Adding threads one by one
            System.out.println("Adding DemoTask : " + threadCounter);
            executor.execute(new MyRunnable(threadCounter.toString()));
            if (threadCounter == 1000)
                break;
        }
    }
}

