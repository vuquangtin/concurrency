package com.hystrix.async.spring.dto;

import java.io.Serializable;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class ErrorMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7029797929234672988L;

	/** The code. */
	private String code;

	/** The message. */
	private String message;

	/**
	 * Instantiates a new error message.
	 */
	public ErrorMessage() {
		super();
	}

	/**
	 * Instantiates a new error message.
	 *
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 */
	public ErrorMessage(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code
	 *            the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[code=" + code + ", message=" + message + "]";
	}

}