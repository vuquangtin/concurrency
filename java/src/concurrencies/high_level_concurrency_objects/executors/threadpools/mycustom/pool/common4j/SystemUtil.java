package threadpools.mycustom.pool.common4j;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class SystemUtil {
	public SystemUtil() {
	}

	public static String getEndLine() {
		return System.getProperty("line.separator");
	}

	public static void main(String[] args) {
		getEndLine();
		getProcessorCount();
		System.out.println(getEndLine() + "ppp");
	}

	public static int getProcessorCount() {
		System.out.println(Runtime.getRuntime().availableProcessors());
		return Runtime.getRuntime().availableProcessors();
	}
}