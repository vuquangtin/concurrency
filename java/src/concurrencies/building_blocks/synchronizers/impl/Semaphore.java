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
public class Semaphore {
    private int max;
    private int count;

    public Semaphore(int max) {
        this.max = max;
    }

    public synchronized void acquire() {
            while (count == max) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count++;
            notify();
    }

    public synchronized void release() {
            while (count == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count--;
            notify();
    }
}