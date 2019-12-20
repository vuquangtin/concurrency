package concurrencies.utilities;

import org.apache.log4j.Logger;

public class LogTest {
	static Logger logger = Logger.getLogger(LogTest.class.getName());
	static Logger log = Logger.getLogger(LogTest.class.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		log = Log4jUtils.initLog4j();
	}

}
