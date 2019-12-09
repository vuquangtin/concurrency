package lockobjects.lock;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class Resource {

	public void doSomething() {
		// do some operation, DB read, write etc
	}

	public void doLogging() {
		// logging, no need for thread safety
	}
}