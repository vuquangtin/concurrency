package com.rxjava3.reactivex.io.flowable.consumeapi;

import io.reactivex.rxjava3.core.Flowable;

import org.apache.log4j.Logger;

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
public class FlowableInvokeUsersAPI {

	static Logger log = Logger.getLogger(LogTest.class.getName());

	public static void main(String[] args) {
		log = Log4jUtils.initLog4j();
		new FlowableInvokeUsersAPI().invokeURL("https://randomuser.me/api/")
				.subscribe(result -> log.info("Result " + result));

	}

	Flowable<String> invokeURL(String url) {
		return Flowable.just(url).map(u -> Utils.invoke(u));
	}

}