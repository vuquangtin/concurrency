package com.enableasync.async.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class LoadConfiguration {
	Logger log = LoggerFactory.getLogger(LoadConfiguration.class);

	public Properties getProperties() throws IOException {
		Properties prop = new Properties();
		String propFileName = "TestFeed.properties";
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(propFileName);

		if (inputStream != null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				log.error("Initialization ERROR: could not load "
						+ propFileName + "", e);
				throw new IOException("Initialization ERROR: could not load "
						+ propFileName + "");
			}
		} else {
			log.error("Initialization ERROR: no TestFeed configuration was found. Please check if "
					+ propFileName + " file is in class path");
			throw new IOException(
					"Initialization ERROR: no TestFeed configuration was found. Please check if "
							+ propFileName + " file is in class path");
		}
		return prop;
	}
}