package concurrency.java.optimize.executor.test;

import org.junit.Test;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExamplesTest {

    @Test(timeout = 5000)
    public void examples() throws InterruptedException {
    	Examples.basicExample();
    	Examples.boundedExecutorExample();
    }
}