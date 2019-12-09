package basic.threads;
/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class JoinThread {
    public static void main(String[] args) {
        Thread thread2 = new Thread(new WaitRunnable());
        Thread thread3 = new Thread(new WaitRunnable());

        System.out.println("Current time millis : " + System.currentTimeMillis());

        thread2.start();

        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Current time millis : " + System.currentTimeMillis());

        thread3.start();

        try {
            thread3.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Current time millis : " + System.currentTimeMillis());
    }

    private static class WaitRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }
}