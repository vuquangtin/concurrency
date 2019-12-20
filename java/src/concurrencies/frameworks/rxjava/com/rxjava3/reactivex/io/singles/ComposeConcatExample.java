package com.rxjava3.reactivex.io.singles;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rxjava3.utils.Utils;

import concurrencies.utilities.Log4jUtils;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ComposeConcatExample {
	static Logger logger = Logger.getLogger(ComposeConcatExample.class
			.getName());

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		try {
			Observable.concat(getMaleObservable(), getFemaleObservable())
					.observeOn(Schedulers.newThread())
					.subscribe(new Observer<User>() {
						@Override
						public void onSubscribe(Disposable d) {
						}

						@Override
						public void onNext(User user) {
							logger.info(user.getName() + ", "
									+ user.getGender());
							//System.out.println(user.getName() + ", "
							//		+ user.getGender());
						}

						@Override
						public void onError(Throwable e) {

						}

						@Override
						public void onComplete() {

						}
					});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("Observable.concat");
//		Observable.concat(
//				Observable.interval(1, TimeUnit.SECONDS).map(id -> "A" + id),
//				Observable.interval(1, TimeUnit.SECONDS).map(id -> "B" + id))
//				.subscribe(System.out::println);
		Utils.sleep(10000);
	}

	public static Observable<User> getFemaleObservable() {
		String[] names = new String[] { "Lucy", "Scarlett", "April" };

		final List<User> users = new ArrayList<>();
		for (String name : names) {
			User user = new User();
			user.setName(name);
			user.setGender("female");
			users.add(user);
		}
		return Observable.create(new ObservableOnSubscribe<User>() {
			@Override
			public void subscribe(ObservableEmitter<User> emitter)
					throws Exception {
				for (User user : users) {
					if (!emitter.isDisposed()) {
						Thread.sleep(1000);
						emitter.onNext(user);
					}
				}

				if (!emitter.isDisposed()) {
					emitter.onComplete();
				}
			}
		}).subscribeOn(Schedulers.newThread());
	}

	public static Observable<User> getMaleObservable() {
		String[] names = new String[] { "Mark", "John", "Trump", "Obama" };

		final List<User> users = new ArrayList<>();

		for (String name : names) {
			User user = new User();
			user.setName(name);
			user.setGender("male");

			users.add(user);
		}
		return Observable.create(new ObservableOnSubscribe<User>() {
			@Override
			public void subscribe(ObservableEmitter<User> emitter)
					throws Exception {
				for (User user : users) {
					if (!emitter.isDisposed()) {
						Thread.sleep(500);
						emitter.onNext(user);
					}
				}

				if (!emitter.isDisposed()) {
					emitter.onComplete();
				}
			}
		}).subscribeOn(Schedulers.newThread());
	}
}
