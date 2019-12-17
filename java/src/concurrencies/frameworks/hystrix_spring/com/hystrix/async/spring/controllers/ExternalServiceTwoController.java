package com.hystrix.async.spring.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;

import concurrencies.utilities.Log4jUtils;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

@RestController
public class ExternalServiceTwoController {

	private static org.apache.log4j.Logger logger = Log4jUtils.initLog4j();

	private static DynamicLongProperty timeToWait = DynamicPropertyFactory
			.getInstance().getLongProperty("hystrixdemo.sleep", 5000);

	public static synchronized List<String> getData(int id)
			throws InterruptedException {
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException();
		}
		long t = timeToWait.get();
		logger.info("waiting {} ms" + t);
		if (t > 0) {
			Thread.sleep(t);
		}

		List<String> list = new ArrayList<String>();
		list.add("Chennai");
		list.add("Jammu Kashmir");
		list.add("Gujarat");
		list.add("Goa");
		list.add("Bihar");
		return list;
	}

	@RequestMapping(path = "serviceTwo/states", method = RequestMethod.GET)
	public List<String> getExternalOneList() {
		try {
			return getData(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;

	}

}