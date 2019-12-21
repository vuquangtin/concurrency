package basic.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Created by xfan on 16/1/21.
 */
public class RunnableExample {

    public static void executeTaskUsingThreadAndRunnable() {
        Runnable task = () -> {
            try {
                String tname = Thread.currentThread().getName();
                System.out.println(tname + ": task started.");
                TimeUnit.SECONDS.sleep(1); // or Thread.sleep(1000);
                System.out.println(tname + ": task completed.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }

    public static void main(String[] args) {
        RunnableExample.executeTaskUsingThreadAndRunnable();
    }
}