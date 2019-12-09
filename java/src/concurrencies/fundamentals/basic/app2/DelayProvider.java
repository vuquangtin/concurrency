package basic.app2;

import java.util.concurrent.TimeUnit;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public interface DelayProvider {
	void executeAfterDelay(long interval, TimeUnit timeUnit, Runnable task);
}