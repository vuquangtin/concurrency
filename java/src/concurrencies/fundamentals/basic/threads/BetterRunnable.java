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
public class BetterRunnable implements Runnable {
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            //Heavy operation
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            //Other operation
        }
    }
}