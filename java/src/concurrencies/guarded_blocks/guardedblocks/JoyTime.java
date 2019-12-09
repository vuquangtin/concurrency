package guardedblocks;


public class JoyTime {

    public static void main(String[] args) {
        JoyRider j1 = new JoyRider(false);
        JoyRider j2 = new JoyRider(true);

        Thread t1 = new Thread(j1, "J1");
        Thread t2 = new Thread(j2, "J2");

        t1.start();

        try {
            Thread.sleep(4000);
        }
        catch (InterruptedException e) {}

        t2.start();
    }
}

class JoyRider implements Runnable {

    private static boolean joy = false;
    private boolean flag;

    public JoyRider(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        synchronized(this) {
            if (flag) {
                notifyJoy();
            }
            else {
                while (!joy) {
                    System.out.println("No Joy Yet...");
                    try {
                        this.wait();
                    }
                    catch (InterruptedException e) {}
                }
                System.out.println("Joy has been achieved!");
            }
        }
    }

    public synchronized void notifyJoy() {
        System.out.println("Notifying Joy");
        joy = true;
        notifyAll();
    }
}