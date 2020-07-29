package learn.threads.vn.docs2;

import java.util.Random;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SimpleThread extends Thread {
    
    private int n = 0;

    public SimpleThread(int k) {
        this.n = k;
    }
    
    public void run() {
        System.out.println("Thread#" + n + " is running...");
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {}
        System.out.println("Thread#" + n + " has been done!");
    }
}