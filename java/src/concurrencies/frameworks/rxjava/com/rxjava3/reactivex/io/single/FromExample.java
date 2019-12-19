package com.rxjava3.reactivex.io.single;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Constructs a sequence from a pre-existing source or generator type.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FromExample {

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(1);
		List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7,
				8));

		Observable<Integer> observable = Observable.fromIterable(list);

		observable.observeOn(Schedulers.io()).subscribe(
				item -> System.out.println(item),
				error -> error.printStackTrace(), () -> {
					System.out.println("Done");
					latch.countDown();
				});
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
