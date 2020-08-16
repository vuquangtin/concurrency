package concurrency.java.optimize.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import concurrency.java.optimize.tasks.PriorityThreadFactory;

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
	   
    public BlockingThreadPoolExecutor() {
        this(1, 1, Integer.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), 
        		new PriorityThreadFactory("testThread"));
    }
    
    public BlockingThreadPoolExecutor(int corePoolSize, int maxPoolSize, int queueCapacity, 
    		String baseThreadName, int priority, boolean daemon) {
        this(corePoolSize, maxPoolSize, Integer.MAX_VALUE, TimeUnit.SECONDS, 
        		new LinkedBlockingQueue<Runnable>(queueCapacity), 
        		new PriorityThreadFactory(baseThreadName, priority, daemon));
    }
    
    /**
     * Creates a new <tt>ThreadPoolExecutor</tt> with the given initial
     * parameters and default rejected execution handler.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if they are idle.
     * @param maximumPoolSize the maximum number of threads to allow in the pool.
     * @param keepAliveTime maximum time that excess idle threads will wait for new tasks before terminating.
     * @param unit the time unit for the keepAliveTime argument.
     * @param workQueue the queue to use for holding tasks before they are executed. 
     * @param threadFactory the factory to use when the executor creates a new thread.
     * @throws IllegalArgumentException if corePoolSize or
     * 		   keepAliveTime less than zero, or if maximumPoolSize less than or
     *         equal to zero, or if corePoolSize greater than maximumPoolSize.
     * @throws NullPointerException if <tt>workQueue</tt> or <tt>threadFactory</tt> are null.
     */
    public BlockingThreadPoolExecutor(int corePoolSize, int maxPoolSize, long keepAliveTime, 
    		TimeUnit unit, BlockingQueue<Runnable> queue, ThreadFactory threadFactory) {
        super(corePoolSize, maxPoolSize, keepAliveTime, unit, queue, threadFactory);
        allowCoreThreadTimeOut(true);
         
        this.setRejectedExecutionHandler(new RejectedExecutionHandler () {
            public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
            	// number of total retries
                int retryCount = 0;
                
                // Try indefinitely to add the task to the queue
                while (true) {
                	retryCount++;
                    
                    if (executor.isShutdown()) {
                    	// Reject the task
                        ((BlockingThreadPoolExecutor) executor).taskRejectedGaveUp(task, retryCount);
                        throw new RejectedExecutionException("Task cannot accepted and will be shut down!");
                    }
                    
                    try {
                    	// Inserts the specified element into this queue, waiting up to the specified 
                    	// wait time if necessary for space to become available.
                        if (executor.getQueue().offer(task, 1, TimeUnit.SECONDS)) {
                            ((BlockingThreadPoolExecutor) executor).taskAccepted(task, retryCount);
                            break;
                        } else {
                            ((BlockingThreadPoolExecutor) executor).taskRejectedRetrying(task, retryCount);
                        }
                        
                    }
                    catch (InterruptedException e) {
                        throw new AssertionError(e);
                    }
                } 
            }
        });
    }
    
    /**
     * Rejected the task.
     */
    protected void taskRejectedGaveUp(Runnable task, int retryCount) {}
    
    /**
     * Retry rejected task.
     */
    protected void taskRejectedRetrying(Runnable task, int retryCount) {}
    
    /**
     * Rejected task accepted.
     */
    protected void taskAccepted(Runnable task, int retryCount) {}
}