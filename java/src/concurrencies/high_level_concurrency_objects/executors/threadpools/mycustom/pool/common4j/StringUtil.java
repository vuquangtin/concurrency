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
public class StringUtil {
	public StringUtil() {
	}

	public static boolean isNull(String str) {
		return null == str;
	}

	public static boolean isEmpty(String str) {
		return null == str || 0 == str.length();
	}

	public static boolean isBlank(String str) {
		if (isEmpty(str)) {
			return true;
		} else {
			for (int i = 0; i < str.length(); ++i) {
				if (!Character.isWhitespace(str.charAt(i))) {
					return false;
				}
			}
			return true;
		}
	}
}