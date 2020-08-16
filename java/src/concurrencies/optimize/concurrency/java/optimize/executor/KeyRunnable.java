package concurrency.java.optimize.executor;

import java.util.Objects;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public final class KeyRunnable<Key> implements Runnable {

    private final Key key;

    private final Runnable runnable;

    public KeyRunnable(Key key, Runnable runnable) {
        this.key = key;
        this.runnable = runnable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyRunnable<?> that = (KeyRunnable<?>) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    @Override
    public String toString() {
        return Objects.toString(key);
    }

    @Override
    public void run() {
        runnable.run();
    }
}