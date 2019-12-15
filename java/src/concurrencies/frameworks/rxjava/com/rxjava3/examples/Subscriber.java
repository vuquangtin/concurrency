package com.rxjava3.examples;

public abstract class Subscriber<T> implements Observer<T> {
	public void onStart() {
		// 可以用于做一些准备工作，例如数据的清零或重置
		// 但如果必须在主线程就不能用了, 只能适用 doOnSubscribe
		String asd = "准备工作";
	}

}