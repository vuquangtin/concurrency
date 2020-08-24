package concurrencies.utilities;

import java.io.File;
import java.io.IOException;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public abstract class Resource {
	public static String PATH = null;
	public static String PATH_NINJAR = null;
	public static String PATH_INTERPRETER = null;
	static {
		try {
			PATH = new File(".").getCanonicalPath() + "/java/src/resources/images/";
			PATH_NINJAR = new File(".").getCanonicalPath()
					+ "/java/resources/app_ninjar/";
			PATH_INTERPRETER = new File(".").getCanonicalPath()
					+ "/java/resources/app_interpreter/";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
}
