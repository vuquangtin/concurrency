package concurrency.java.optimize.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public interface DrainableExecutor extends Executor {

    boolean drain(long timeout, TimeUnit unit) throws InterruptedException;
}