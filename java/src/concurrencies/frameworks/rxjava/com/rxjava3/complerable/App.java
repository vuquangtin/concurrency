package com.rxjava3.complerable;

import java.io.IOException;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class App {
	public static void main(String[] args) throws InterruptedException, IOException {

		// simple
		// -------------------//
		Flowable.just("Hello world").subscribe(System.out::println);

		// with consumer
		// -------------------//
		Flowable.just(new ModelKu("hello world")).subscribe(new Consumer<ModelKu>() {
			@Override
			public void accept(ModelKu s) {
				System.out.println(s.Data);
			}
		});

		// simulate thread
		// -------------------//
		Flowable.fromCallable(() -> {
			Thread.sleep(1000);
			return new ModelKu("Done");
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single()).subscribe(new Consumer<ModelKu>() {
			@Override
			public void accept(ModelKu s) {
				System.out.println(s.Data);
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable s) {
				System.out.println(s.getMessage());
			}
		});
		Thread.sleep(2000);

		// request with okhhtp
		// -------------------//
		final OkHttpClient client = new OkHttpClient();
		final Request request = new Request.Builder().url("https://pariwisata-5a943.firebaseio.com/lokasi_wisata.json")
				.get().addHeader("application/json", "charset=utf-8").build();

		// using flowable
		Flowable.create(new FlowableOnSubscribe<Response>() {
			@Override
			public void subscribe(FlowableEmitter<Response> subscriber) {
				try {
					Response response = client.newCall(request).execute();
					subscriber.onNext(response);
					subscriber.onComplete();
				} catch (IOException e) {
					e.printStackTrace();
					subscriber.onError(e);
				}
			}
		}, BackpressureStrategy.BUFFER).subscribe(new Consumer<Response>() {
			@Override
			public void accept(Response s) throws IOException {
				System.out.println(s.body().string());
			}
		});

		// using Observable
		Observable.create(new ObservableOnSubscribe<Response>() {
			@Override
			public void subscribe(ObservableEmitter<Response> subscriber) {
				try {
					Response response = client.newCall(request).execute();
					subscriber.onNext(response);
					subscriber.onComplete();
				} catch (IOException e) {
					e.printStackTrace();
					subscriber.onError(e);
				}
			}

		}).subscribe(new Observer<Response>() {
			@Override
			public void onSubscribe(Disposable d) {
				System.out.println(d.isDisposed() ? "disposed" : "dispose");
			}

			@Override
			public void onNext(Response s) {
				try {
					System.out.println(s.body().string());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onComplete() {
				System.out.println("request complete");
			}

		});
	}

	public static class ModelKu {
		public String Data = "";

		public ModelKu(String data) {
			this.Data = data;
		}
	}
}