package com.rxjava3.reactivex.io.schedulers;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rxjava3.reactivex.io.flowable.operators.FlowableZipSynchronous;
import com.rxjava3.utils.Utils;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SchedulerTest {

	private static final Logger log = LoggerFactory
			.getLogger(FlowableZipSynchronous.class);

	// public static void basic() {
	// String[] objs = { "1-S", "2-T", "3-P" };
	// Observable<String> source = Observable.fromArray(objs)
	// .doOnNext(data -> log.info("Origin Data = " + data))
	// .subscribeOn(Schedulers.newThread())
	// .observeOn(Schedulers.newThread()).map(Shape::flip);
	// source.subscribe(log::info);
	// Utils.sleep(500);
	// }

	public static void newThead() {
		String[] objs = { "1", "3", "5" };
		Observable.fromArray(objs)
				.doOnNext(data -> log.info("Original data : " + data))
				.map(data -> "<<" + data + ">>")
				.subscribeOn(Schedulers.newThread()).subscribe(log::info);

		Utils.sleep(500);

		Observable.fromArray(objs)
				.doOnNext(data -> log.info("Original data : " + data))
				.map(data -> "##" + data + "##")
				.subscribeOn(Schedulers.newThread()).subscribe(log::info);
		Utils.sleep(500);
	}

	public static void computation() {
		String[] objs = { "1", "3", "5" };
		Observable<String> source = Observable.fromArray(objs).zipWith(
				Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

		source.map(item -> "<<" + item + ">>")
				.subscribeOn(Schedulers.computation()).subscribe(log::info);

		source.map(item -> "##" + item + "##")
				.subscribeOn(Schedulers.computation()).subscribe(log::info);
		Utils.sleep(1000);
	}

	public static void io() {
		String root = "/";
		File[] files = new File(root).listFiles();
		Observable<String> source = Observable.fromArray(files)
				.filter(f -> !f.isDirectory()).map(f -> f.getAbsolutePath())
				.subscribeOn(Schedulers.io());
		source.subscribe(log::info);
		Utils.sleep(500);
	}

	public static void trampoline() {

		String[] objs = { "1", "3", "5" };
		Observable<String> source = Observable.fromArray(objs);
		source.subscribeOn(Schedulers.trampoline())
				.map(data -> "<<" + data + ">>").subscribe(log::info);
		source.subscribeOn(Schedulers.trampoline())
				.map(data -> "##" + data + "##").subscribe(log::info);
	}

	public static void single() {
		Observable<Integer> numbers = Observable.range(100, 5);
		Observable<String> chars = Observable.rangeLong(0l, 5l).map(
				Utils::numberToAlphabet);

		// numbers.subscribeOn(Schedulers.single()).subscribe(log::info);

		chars.subscribeOn(Schedulers.single()).subscribe(log::info);
		Utils.sleep(500);

	}

	public static void executor() {

		final int THREAD_NUM = 10;
		String[] data = { "1", "3", "5" };
		Observable<String> source = Observable.fromArray(data);
		Executor executor = Executors.newFixedThreadPool(THREAD_NUM);

		source.subscribeOn(Schedulers.from(executor)).subscribe(log::info);

		source.subscribeOn(Schedulers.from(executor)).subscribe(log::info);
		Utils.sleep(500);
	}

	public static void main(String[] args) {
		single();
	}
}