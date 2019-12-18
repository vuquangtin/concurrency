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
public class MapOnSubscribe<T, R> implements Observable.OnSubscribe<R> {
	final Observable<T> source;
	final Transformer<? super T, ? extends R> transformer;

	public MapOnSubscribe(Observable<T> source, Transformer<? super T, ? extends R> transformer) {
		this.source = source;
		this.transformer = transformer;
	}

	@Override
	public void call(Subscriber<? super R> subscriber) {
		source.subscribe(new MapSubscriber<R, T>(subscriber, transformer));
	}
}