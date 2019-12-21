package concurrencies.frameworks.hystrixs;

import static java.lang.Thread.sleep;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;


public class HotNCold {

	public static void main(String[] args) throws InterruptedException {
		Observable obs = Observable.interval(10, 100, TimeUnit.MILLISECONDS);
		Disposable sub = obs.subscribe(System.out::println);
		sleep(520);
		obs.subscribe(System.out::println);
		sleep(520);

	}

	public void fusk() throws InterruptedException {
		Observable obs = Observable.interval(10, 100, TimeUnit.MILLISECONDS);
		Disposable sub = obs.subscribe(System.out::println);
		sleep(520);
		//sub.unsubscribe();
		obs.subscribe(System.out::println);
		sleep(520);

		Observable obs2 = Observable.interval(10, 100, TimeUnit.MILLISECONDS);
		ConnectableObservable conObs = obs2.publish();
		//Subscription sub2 = conObs.subscribe(System.out::println);
		conObs.connect();
		sleep(530);
		//sub2.unsubscribe();
		sleep(530);
		conObs.subscribe(System.out::println);
		sleep(530);

	}
}