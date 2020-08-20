package concurrencies.utilities;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jUtils {
	private final static String LOG_NAME = "start-the-any-browser";

	public static Logger initLog4j() {
		Properties properties = new Properties();
		properties.setProperty("log4j.rootLogger", "TRACE,stdout,MyFile");
		properties.setProperty("log4j.rootCategory", "TRACE");

		properties.setProperty("log4j.appender.stdout",
				"org.apache.log4j.ConsoleAppender");
		properties.setProperty("log4j.appender.stdout.layout",
				"org.apache.log4j.PatternLayout");
		// properties.setProperty("log4j.appender.stdout.layout.ConversionPattern","%d{yyyy/MM/dd
		// HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");

		// properties.setProperty("log4j.appender.stdout","org.apache.log4j.ConsoleAppender");

		properties.setProperty("log4j.appender.MyFile",
				"org.apache.log4j.RollingFileAppender");
		properties.setProperty("log4j.appender.MyFile.File", "logs/" + LOG_NAME
				+ "-" + TimeUtils.getNow() + ".log");

		properties.setProperty("log4j.appender.MyFile.MaxFileSize", "100mb");

		properties.setProperty("log4j.appender.MyFile.MaxBackupIndex", "1");
		properties.setProperty("log4j.appender.MyFile.layout",
				"org.apache.log4j.PatternLayout");
		// properties.setProperty("log4j.appender.MyFile.layout.ConversionPattern","%d{yyyy/MM/dd
		// HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");
		properties.setProperty(
				"log4j.appender.MyFile.layout.ConversionPattern",
				"%d %5p [%l] - %m%n");

		properties.setProperty("log4j.appender.stdout.layout",
				"org.apache.log4j.PatternLayout");
		// properties.setProperty("log4j.appender.stdout.layout.ConversionPattern","%5p
		// | %d | %F | %L | %m%n");
		properties.setProperty(
				"log4j.appender.stdout.layout.ConversionPattern",
				"%d %5p %l - %m%n");
		properties.setProperty("log4j.category.com.restfb", "WARN,file");
		properties.setProperty("log4j.additivity.com.restfb", "false");
		properties.setProperty("log4j.category.com.mongodb", "WARN,file");
		properties.setProperty("log4j.additivity.com.mongodb", "false");
		// %d %5p [%l] - %m%n
		PropertyConfigurator.configure(properties);
		Logger logger = Logger.getLogger("log4j");

		logger.fatal("This is a FATAL message.");
		logger.error("This is an ERROR message.");
		logger.warn("This is a WARN message.");
		logger.info("This is an INFO message.");
		logger.debug("This is a DEBUG message.");
		logger.trace("This is a TRACE message.");
		return logger;
	}

	public static void echo(String text) {
		System.out.println(text);
	}
}
