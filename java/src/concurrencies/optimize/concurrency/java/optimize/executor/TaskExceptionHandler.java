package concurrency.java.optimize.executor;

import java.util.concurrent.ExecutionException;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public interface TaskExceptionHandler<Key> {

    default void onException(Key key, Throwable cause) {
        new ExecutionException("exception thrown by a task for the key '" + key + "'", cause).printStackTrace();
    }
}