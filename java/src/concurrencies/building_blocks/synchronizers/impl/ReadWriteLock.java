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
public class ReadWriteLock {
    private int writeRequests;
    private int readCount;
    private int writeCount;

    public synchronized void readLock() {
        while (writeRequests > 0 || writeCount > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            readCount++;
        }
    }

    public synchronized void readUnlock() {
        readCount--;
        notifyAll();
    }

    public synchronized void writeLock() {
        writeRequests++;
        while (writeCount > 0 || readCount > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        writeRequests--;
        writeCount++;
    }

    public synchronized void writeUnlock() {
        writeCount--;
        notifyAll();
    }
}