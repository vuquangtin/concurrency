package basic.getresults;

import java.util.Random;

public class RunnableDemo implements Runnable {
    private Object result = null;

    @Override
    public void run() {
        Random generator = new Random();
        Integer randomNumber = generator.nextInt(5);

        try {
            Thread.sleep(randomNumber * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        result = randomNumber;

        synchronized (this) {
            notifyAll();
        }
    }

    public synchronized Object get() throws InterruptedException {
        while (result == null) {
            wait();
        }
        return result;
    }
}