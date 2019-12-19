package com.rxjava3.tutorials;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class Debounce {

	public static void main(String[] args) {
		Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
			int totalTime = 0;
			for (int i = 0; i < 20; i++) {
				int sleepTime = i * 10;
				TimeUnit.MILLISECONDS.sleep(sleepTime);
				totalTime += sleepTime;
				emitter.onNext(totalTime);
			}
			emitter.onComplete();
		}).debounce(50, TimeUnit.MILLISECONDS).subscribe(System.out::println);
	}

	private static void test1() {
		Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
			Random random = new Random(47);
			int totalTime = 0;
			for (int i = 0; i < 20; i++) {
				int ranNum = random.nextInt(10);
				int sleepTime = ranNum * 10;
				TimeUnit.MILLISECONDS.sleep(sleepTime);
				totalTime += sleepTime;
				emitter.onNext(totalTime);
			}
			emitter.onComplete();
		}).debounce(50, TimeUnit.MILLISECONDS).subscribe(System.out::println);
	}
}