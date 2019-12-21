package basic.threads;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchWorker {
	 
    public CountDownLatch count = new CountDownLatch(2000);
 
    public static void main(String[] args) {
    	CountDownLatchWorker worker = new CountDownLatchWorker();
        worker.doWork();
    }
     
     
 
    public void doWork() {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                	try {
						Thread.sleep(new Random().nextInt(10));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    count.countDown();
                    System.out.println("run "+i);
                }
            }
        });
         
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                	try {
						Thread.sleep(new Random().nextInt(10));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    count.countDown();
                    System.out.println("run "+i);
                }
            }
        });
         
        thread1.start();
        thread2.start();
 
        try {
            count.await();
        } catch (InterruptedException ignored) {
        }
        System.out.println("Count is: " + count.getCount());
    }
}