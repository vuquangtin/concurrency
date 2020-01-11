package threadpools.mycustom;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class DefaultResultListener<T> implements ResultListener<T> {

	@Override
	public void finish(T obj) {

	}

	@Override
	public void error(Exception ex) {
		ex.printStackTrace();
	}

}
