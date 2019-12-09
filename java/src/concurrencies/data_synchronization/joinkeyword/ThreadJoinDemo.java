package joinkeyword;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/3/17 19:49
 */
public class ThreadJoinDemo {
    public static void main(String[] args) {
        Thread t1 = new JoinThread("T1");
        Thread t2 = new JoinThread("T2");

        try {
            System.out.println("Wait for thre child threads to finish.");

            t1.join();
            if (!t1.isAlive()){
                System.out.println("Thread T1 is not alive.");
            }

            t2.join();
            if (!t2.isAlive()){
                System.out.println("Thread T2 is not alive.");
            }

        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Exit from Main Thread.");
    }
}


class JoinThread extends Thread {

    private final Random random = ThreadLocalRandom.current();

    public JoinThread(String name) {
        super(name);
        start();
    }

    void randWait() {
        try {
            sleep((long) (3000 * random.nextDouble()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        System.out.println(getName() + ": is running.");
        for (int i = 0; i < 3; i++) {
            System.out.println(getName() + ": " + i);
            randWait();
        }
    }
}