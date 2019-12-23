package com.rxjava.books.rxpwithrxjava;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Tweet {

	private final String text;

	Tweet(String text) {
		this.text = text;
	}

	String getText() {
		return text;
	}
}
