package concurrency.java.memory.model.jmm;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ShowStackOverFlowError {

	public static void main(String[] args) {

		methodOne();
	}

	public static void methodOne() {
		System.out.println("Method One");
		methodTwo();
	}

	public static void methodTwo() {

		System.out.println("Method Two");
		methodOne();
	}
}