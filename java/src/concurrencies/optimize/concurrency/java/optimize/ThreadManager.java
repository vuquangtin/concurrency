package concurrency.java.optimize;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
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
public class ThreadManager {

    private static ThreadManager threadManager;
    private final ThreadPoolExecutor poolExecutor;

    private ThreadManager(){
        poolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static ThreadManager getThreadManager() {
        if (threadManager == null){
            synchronized (ThreadManager.class){
                if (threadManager == null){
                    threadManager = new ThreadManager();
                }
            }
        }
        return threadManager;
    }

    /**
     * 执行任务
     */
    public void execute(Runnable runnable){
        if(runnable==null)return;

        poolExecutor.execute(runnable);
    }
    /**
     * 从线程池中移除任务
     */
    public void remove(Runnable runnable){
        if(runnable==null)return;

        poolExecutor.remove(runnable);
    }
}