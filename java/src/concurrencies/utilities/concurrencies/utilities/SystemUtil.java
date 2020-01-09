package concurrencies.utilities;

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
	public static int getCpuCoreSize() {
		return Runtime.getRuntime().availableProcessors();
	}

	public static int getRecommendThreadSize() {
		int calcTaskTime = 200;
		int waitTime = 3000;
		return getCpuCoreSize() * (calcTaskTime + waitTime) / calcTaskTime;
	}
}