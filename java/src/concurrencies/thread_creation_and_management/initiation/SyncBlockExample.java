package initiation;


import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/17 18:00
 */
public class SyncBlockExample extends Thread {
    static Integer[] msg = {1, 2, 3, 4, 5};
    private final Random random = ThreadLocalRandom.current();

    public SyncBlockExample(String name) {
        super(name);
    }

    void randomWait() {
        try {
            sleep((long) (300 * random.nextDouble()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        synchronized (random) {
            for (int i = 0; i < msg.length; i++) {
                randomWait();
                System.out.println(getName() + ": " + msg[i]);
            }
        }
    }

    public static void main(String[] args) {
        SyncBlockExample t1 = new SyncBlockExample("thread1");
        SyncBlockExample t2 = new SyncBlockExample("thread2");
        t1.start();
        t2.start();

        boolean t1IsAlive = true;
        boolean t2IsAlive = true;
        do {
            if (t1IsAlive && !t1.isAlive()) {
                t1IsAlive = false;
                System.out.println(t1.getName() + " is dead");
            }

            if (t2IsAlive && !t2.isAlive()) {
                t2IsAlive = false;
                System.out.println(t2.getName() + " is dead");
            }
        } while (t1IsAlive || t2IsAlive);
        System.out.println(Thread.currentThread().getName() + ": done");
    }
}