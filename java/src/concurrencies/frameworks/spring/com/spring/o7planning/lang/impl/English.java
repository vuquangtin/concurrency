package com.spring.o7planning.lang.impl;

import com.spring.o7planning.lang.Language;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class English implements Language{

	@Override
	public String getGreeting() {
		// TODO Auto-generated method stub
		return "Hello";
	}

	@Override
	public String getBye() {
		// TODO Auto-generated method stub
		return "Good bye!!!";
	}

}