package com.hystrix.async.spring.models;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Context {
	private long spanId;
	private String traceIdString;
	public long getSpanId() {
		return spanId;
	}
	public void setSpanId(long spanId) {
		this.spanId = spanId;
	}
	public String getTraceIdString() {
		return traceIdString;
	}
	public void setTraceIdString(String traceIdString) {
		this.traceIdString = traceIdString;
	}

	
}
