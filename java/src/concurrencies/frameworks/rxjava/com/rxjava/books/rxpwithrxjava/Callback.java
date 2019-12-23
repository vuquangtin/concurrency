package com.rxjava.books.rxpwithrxjava;

import java.util.function.Consumer;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class Callback {
	private Consumer<String> onResponse = x -> {
	};
	private Consumer<Exception> onFailure = x -> {
	};

	Callback onResponse(Consumer<String> consumer) {
		this.onResponse = consumer;
		return this;
	}

	Callback onFailure(Consumer<Exception> consumer) {
		this.onFailure = consumer;
		return this;
	}

	public Consumer<String> getOnResponse() {
		return onResponse;
	}

	public Consumer<Exception> getOnFailure() {
		return onFailure;
	}
}
