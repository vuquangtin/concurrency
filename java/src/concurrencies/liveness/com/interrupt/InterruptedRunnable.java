package com.interrupt;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class InterruptedRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread started");
        while (true) {
            if (Thread.interrupted()) {
                System.out.println("Thread was interrupted");
                break;
            }
        }
        System.out.println("Thread stopped");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new InterruptedRunnable());
        thread.start();
        Thread.sleep(2_000);
        thread.interrupt();
    }
}