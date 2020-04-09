package com.patterns.activeobject.impl;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public interface ActiveObject {
	public abstract Result<String> makeString(int count, char fillchar);

	public abstract void displayString(String string);
}