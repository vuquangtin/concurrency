package com.deadlocks.utils;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public final class ExitCode {

	/**
	 * Private constructor to avoid instantiation
	 */
	private ExitCode() {
	}

	/**
	 * Indicates that server successfully finished it's work
	 */
	public static final int CODE_NORMAL = 0;

	/**
	 * Indicates that server successfully finished it's work and should be
	 * restarted
	 */
	public static final int CODE_RESTART = 2;

	/**
	 * Indicates that error happened in server and it's need to shutdown.<br>
	 * Shit happens :(
	 */
	public static final int CODE_ERROR = 1;
}
