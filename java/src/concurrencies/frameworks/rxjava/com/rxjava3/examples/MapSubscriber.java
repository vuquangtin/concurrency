package com.rxjava3.examples;

public class MapSubscriber<T, R> extends Subscriber<R> {
	final Subscriber<? super T> actual;
	final Transformer<? super R, ? extends T> transformer;

	public MapSubscriber(Subscriber<? super T> actual, Transformer<? super R, ? extends T> transformer) {
		this.actual = actual;
		this.transformer = transformer;
	}

	// ...........省略
	@Override
	public void onNext(R var1) {
		actual.onNext(transformer.call(var1));
	}

}