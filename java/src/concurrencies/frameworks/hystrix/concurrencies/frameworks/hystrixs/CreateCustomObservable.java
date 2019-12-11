package concurrencies.frameworks.hystrixs;

import rx.Observable;
import rx.Subscriber;

public class CreateCustomObservable {

	public static void main(String[] args) {

		// Skapa en observable med Observable.create
		Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {

			}
		});
		new CreateCustomObservable().fusk();
	}

	public void fusk() {
		Observable obs = Observable.create((subscriber) -> {
			subscriber.onNext(1);
			subscriber.onNext(2);
			subscriber.onNext(3);
		});

		obs.subscribe(System.out::println);
		obs.subscribe(System.out::println);
	}
}