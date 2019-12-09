package basic.runables;
/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class ThreadJoinExample {
    public static void main(String[] args) {
        // Create Thread 1
        Thread thread1 = new Thread(() -> {
            System.out.println("Entered Thread 1");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Exiting Thread 1");
        });

        // Create Thread 2
        Thread thread2 = new Thread(() -> {
            System.out.println("Entered Thread 2");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Exiting Thread 2");
        });

        System.out.println("Starting Thread 1");
        thread1.start();

        System.out.println("Waiting for Thread 1 to complete");
        try {
            thread1.join(1000);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }

        System.out.println("Waited enough! Starting Thread 2 now");
        thread2.start();
    }
}
