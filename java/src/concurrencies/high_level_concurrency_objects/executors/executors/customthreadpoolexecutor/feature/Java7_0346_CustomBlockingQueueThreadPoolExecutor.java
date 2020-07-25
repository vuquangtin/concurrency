package executors.customthreadpoolexecutor.feature;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
 
public class Java7_0346_CustomBlockingQueueThreadPoolExecutor extends ThreadPoolExecutor {
 
    public Java7_0346_CustomBlockingQueueThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
 
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        System.out.println("Perform beforeExecute() logic");
    }
 
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t != null) {
            System.out.println("Perform exception handler logic");
        }
        System.out.println("Perform afterExecute() logic");
    }
 
}

