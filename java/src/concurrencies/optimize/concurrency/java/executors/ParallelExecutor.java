package concurrency.java.executors;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public interface ParallelExecutor {

    void execute(Runnable runnable);

    void execute(Runnable runnable, int hash);

    void shutdown();

    int getPoolSize();

    int getActiveCount();
}
