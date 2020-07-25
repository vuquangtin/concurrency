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
public class MakeStringRequest extends MethodRequest<String> {
	private final int count;
	private final char fillchar;

	public MakeStringRequest(Servant servant, FutureResult<String> future, int count, char fillchar) {
		super(servant, future);
		this.count = count;
		this.fillchar = fillchar;
	}

	public void execute() {
		Result<String> result = servant.makeString(count, fillchar);
		future.setResult(result);
	}
}