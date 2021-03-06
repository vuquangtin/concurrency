package concurrency.java.optimize.executor;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Util {

    static void checkNotNull(Runnable task) {
        if (task == null) {
            throw new NullPointerException("task is null");
        }
    }
}