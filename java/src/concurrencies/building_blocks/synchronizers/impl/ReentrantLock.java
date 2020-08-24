package synchronizers.impl;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ReentrantLock {
    private Thread lockingThread;
    private int locksCount;

    public synchronized void lock() {
        while ((locksCount > 0) && (lockingThread != Thread.currentThread())) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        locksCount++;
        lockingThread = Thread.currentThread();
    }

    public synchronized void unlock() {
        if (lockingThread == Thread.currentThread()) {
            locksCount--;
        }

        if (locksCount == 0) {
            notify();
        }
    }
}
