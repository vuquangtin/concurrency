package concurrency.java.optimize.executor.test;
import org.junit.Test;

import concurrency.java.optimize.executor.KeyRunnable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */


public class KeyRunnableTest {

    private final Runnable r1 = new KeyRunnable<>("1", System::getenv);
    private final Runnable r2 = new KeyRunnable<>("1", System::currentTimeMillis);
    private final Runnable r3 = new KeyRunnable<>("2", System::currentTimeMillis);
    private final Runnable rNull = new KeyRunnable<>(null, System::currentTimeMillis);

    @Test
    public void keyRunnableHashCode() {
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r2.hashCode(), r3.hashCode());
        assertEquals(r1.hashCode(), "1".hashCode());
        assertEquals(rNull.hashCode(), 0);
    }

    @Test
    public void keyRunnableEquals() {
        assertEquals(r1, r2);
        assertNotEquals(r2, r3);
        assertNotEquals(rNull, r3);
        assertNotEquals(r3, rNull);
    }

    @Test
    public void keyRunnableToString() {
        assertEquals(r1.toString(), "1");
        assertEquals(rNull.toString(), "null");
    }
}