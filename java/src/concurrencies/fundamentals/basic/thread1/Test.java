package basic.thread1;

public class Test extends Thread {

    boolean keepRunning = true;

    public static void main(String[] args) throws InterruptedException {
        Test t = new Test();
        t.start();
        Thread.sleep(1000);
        t.keepRunning = false;
        System.out.println(System.currentTimeMillis() + ": keepRunning is false");
    }

    public void run() {
        while (keepRunning) 
            System.out.println(System.currentTimeMillis() + ": " + keepRunning);
    }
}