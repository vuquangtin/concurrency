package concurrencies.frameworks.hystrixs.core;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixObservableCommand;

import concurrencies.frameworks.hystrixs.async.User;

public class AsyncCommand<T> extends HystrixObservableCommand<T> {

    private Callable<T> callable;
    private T fallback;

    protected AsyncCommand(String group, String name, Callable<T> callable, T fallback) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(group)).
                andCommandKey(HystrixCommandKey.Factory.asKey(name)));
        this.callable = callable;
        this.fallback = fallback;
    }

    public AsyncCommand(String group, String name, Callable<T> callable) {
        this(group, name, callable, null);
    }

    @Override
    protected Observable<T> construct() {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(callable.call());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
    @Override
	protected Observable<T> resumeWithFallback() {

		return Observable.create(new Observable.OnSubscribe<T>() {
			@Override
			public void call(Subscriber<? super T> subscriber) {
				try {
					subscriber.onNext(callable.call());
					subscriber.onCompleted();
				} catch (Exception ex) {
					subscriber.onError(ex);
				}
			}
		});
	}
    public T getFallback() {
        return fallback;
    }
}
