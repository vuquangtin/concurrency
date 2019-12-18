package com.rxjava.examples;

/**
 * quote wrapper model entity
 *
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * 
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class QuoteResource {

	String type;
	Quote value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Quote getValue() {
		return value;
	}

	public void setValue(Quote value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "QuoteResource{\n" + "\ttype='" + type + '\'' + ", \n\tvalue="
				+ value + "}\n";
	}
}