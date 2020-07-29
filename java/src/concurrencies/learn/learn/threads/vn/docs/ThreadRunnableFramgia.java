package learn.threads.vn.docs;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadRunnableFramgia implements Runnable {
   // @Override
    public void run1() {
        System.out.println();
        for (int x = 1; x <= 3; x++) {
            System.out.println(x + " Thread name: " + Thread.currentThread().getName());
        }
    }
    @Override
    public void run() {
        System.out.println();
        for (int x = 1; x <= 3; x++) {
            //Ghi ra log thread id cua luong dang chay
            System.out.println(x + " Thread id: " + Thread.currentThread().getId());
        }
    }
}