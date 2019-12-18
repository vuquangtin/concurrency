package com.rxjava.examples;

/**
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * 
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * to store the quotes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

	int id;
	String quote;
	String threadName;

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	@Override
	public String toString() {
		return "[ \n\t\tid-" + threadName + ":\n\t\t" + quote + "\n\t]\n";
	}

}