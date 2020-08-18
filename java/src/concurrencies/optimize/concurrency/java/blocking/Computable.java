package concurrency.java.blocking;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public interface Computable<A, V> {

	/**
	 * 大量计算接口
	 * 
	 * @param arg
	 * @return
	 * @throws InterruptedException
	 */
	V compute(A arg) throws InterruptedException;
}