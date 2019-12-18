package com.rxjava3.examples;
/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public abstract class Subscriber<T> implements Observer<T> {
	public void onStart() {
		// 可以用于做一些准备工作，例如数据的清零或重置
		// 但如果必须在主线程就不能用了, 只能适用 doOnSubscribe
		String asd = "准备工作";
	}

}