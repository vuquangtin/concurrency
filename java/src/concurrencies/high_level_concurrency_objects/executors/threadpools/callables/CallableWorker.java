package threadpools.callables;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class CallableWorker implements Callable<Integer> {
	 
    private int num;
    private Random ran;
 
    public CallableWorker(int num) {
        this.num = num;
        this.ran = new Random();
    }
 
    public Integer call() throws Exception {
        Thread.sleep(ran.nextInt(10) * 1000);
        int result = num * num;
        System.out.println("Complete " + num);
        return result;
    }
}