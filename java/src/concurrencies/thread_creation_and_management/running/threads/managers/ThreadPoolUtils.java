package running.threads.managers;

import java.util.Random;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class ThreadPoolUtils {

	/**
	 * 生成一个随机的long 数据
	 *
	 * @return long
	 */
	public static long generateRandom() {
		Random random = new Random();
		long randomL = random.nextLong();

		return randomL;

	}

}