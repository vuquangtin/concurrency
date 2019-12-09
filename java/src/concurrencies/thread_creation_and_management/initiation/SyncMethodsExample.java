package initiation;


import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/17 17:29
 */
public class SyncMethodsExample extends Thread {
    static Integer[] msg = {1, 2, 3, 4, 5};

    private Random random = ThreadLocalRandom.current();

    public SyncMethodsExample(String name) {
        super(name);
    }

    /**
     * 模拟等待
     */
    void randomWait() {
        try {
            sleep((long) (300 * random.nextDouble()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        SynchronizedOutput.displayList(getName(),msg);
    }


    public static void main(String[] args) {
        SyncMethodsExample t1 = new SyncMethodsExample("thread1");
        SyncMethodsExample t2 = new SyncMethodsExample("thread2");
        t1.start();
        t2.start();

        boolean t1IsAlive = true;
        boolean t2IsAlive = true;

        do {
            if (t1IsAlive && !t1.isAlive()){
                t1IsAlive = false;
                System.out.println(t1.getName()+" is dead");
            }

            if (t2IsAlive && !t2.isAlive()){
                t2IsAlive = false;
                System.out.println(t2.getName()+" is dead");
            }
        } while (t1IsAlive || t2IsAlive);

        System.out.println(Thread.currentThread().getName()+": done");
    }

}

class SynchronizedOutput {
    // if the 'synchronized' keyword is removed, the message is displayed in random fashion
    public static synchronized void displayList(String name, Integer[] msg) {
        for (int i = 0; i < msg.length; i++) {
            SyncMethodsExample t = (SyncMethodsExample) Thread.currentThread();
            t.randomWait();
            System.out.println(name + ": " + msg[i]);
        }
    }
}