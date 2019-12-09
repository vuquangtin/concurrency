package basic.beginner;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
@NotThreadSafe
public class ConnectionPoolWatchDog1 {

	private AtomicInteger borrowers; // we increment this counter
	private AtomicInteger connections; // we decrement thsi counter

	public ConnectionPoolWatchDog1() {
		this.borrowers = new AtomicInteger(1);
		this.connections = new AtomicInteger(10);
	}

	public void borrowOne() {
		// do some tasks
		// increment the borrowers count
		this.borrowers.getAndIncrement();
		// decrement the remaining conenction count
		this.connections.getAndDecrement();
		// do some other tasks
	}

	public void returnOne() {
		// do some tasks
		// increment the remaining conenction count
		this.connections.getAndIncrement();
		// decrement the borrowers count
		this.borrowers.getAndDecrement();
		// do some other tasks
	}
}