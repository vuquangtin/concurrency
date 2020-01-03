package com.rxjava3.reactivex.io.flowable.consumeapi;

import io.reactivex.rxjava3.core.Flowable;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.jayway.jsonpath.JsonPath;
import com.rxjava3.utils.Utils;

import concurrencies.utilities.Log4jUtils;
import concurrencies.utilities.LogTest;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FlowableFilterUsersByGender {

	static Logger log = Logger.getLogger(LogTest.class.getName());

	public static void main(String[] args) {
		log = Log4jUtils.initLog4j();
		new FlowableFilterUsersByGender().filterByGender("male").subscribe(
				result -> log.info("Result " + result));

	}

	Flowable<LinkedHashMap> filterByGender(String gender) {
		Flowable flowable = invokeURL("https://randomuser.me/api/?results=10")
				.flatMap(
						res -> Flowable
								.fromIterable((Collection) JsonPath.parse(res)
										.read("$.results", Collection.class)))
				.filter(a -> ((LinkedHashMap<Object, Object>) a).get("gender")
						.equals(gender));
		return flowable;
	}

	Flowable<String> invokeURL(String url) {
		return Flowable.just(url).map(u -> Utils.invoke(u));
	}

}