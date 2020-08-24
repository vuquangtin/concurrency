package synchronizers.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadPool {
    private List<ThreadForPool> pool;
    public java.util.concurrent.BlockingQueue<Runnable> tasks;
    private boolean isStopped;

    class ThreadForPool extends Thread {
        private java.util.concurrent.BlockingQueue<Runnable> tasks;
        private boolean isStopped;

        ThreadForPool(java.util.concurrent.BlockingQueue<Runnable> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            while (!isStopped) {
                try {
                    tasks.take().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public synchronized void setStopped() {
            isStopped = true;
            this.interrupt();
        }
    }

    public ThreadPool(int numOfThreads, int maxNumOfTasks) {
        pool = new ArrayList<>(numOfThreads);
        tasks = new ArrayBlockingQueue<>(maxNumOfTasks);
        for (int i = 0; i < numOfThreads; i++) {
            pool.add(new ThreadForPool(tasks));
        }

        for (Thread thread: pool) {
            thread.start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (isStopped) {
            throw new IllegalStateException("Thread pool is stopped");
        }
        try {
            tasks.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void stop() {
        isStopped = true;
        for (ThreadForPool thread : pool) {
            thread.setStopped();
        }
    }
}