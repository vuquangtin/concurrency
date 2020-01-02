package com.spring.o7planning.bean;

import java.util.Date;

import org.springframework.stereotype.Repository;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
@Repository
public class MyRepository {
	public String getAppName() {
		return "Hello Spring App";
	}

	public Date getSystemDateTime() {
		return new Date();
	}
}