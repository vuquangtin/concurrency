package concurrencies.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class BotLogger {
	public static Logger get(String name) {
		return LoggerFactory.getLogger(name);
	}

	public static Logger get(Class<?> cls) {
		return get(cls.getName());
	}
}
