package com.enableasync.async.utils;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TrasportException extends Exception {
	private static final long serialVersionUID = 1L;

	public TrasportException(String msg, Exception e) {
		super(msg, e);

	}

}