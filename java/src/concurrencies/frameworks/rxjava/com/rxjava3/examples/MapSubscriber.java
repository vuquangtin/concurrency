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