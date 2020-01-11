package threadpools.mycustom;

/**
 * 
 * This interface imposes finish method which is used to get the {@link Output}
 * object of finished task
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public interface ResultListener<T>
{

	public void finish(T obj);

	public void error(Exception ex);

}
