package concurrencies.frameworks.hystrixs;

import org.apache.log4j.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;

import concurrencies.utilities.Log4jUtils;

public class HystrixObservableCommandHelloWorld extends
		HystrixObservableCommand<String> {
	static Logger logger = Logger
			.getLogger(HystrixObservableCommandHelloWorld.class.getName());
	private final String name;

	public HystrixObservableCommandHelloWorld(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}

	@Override
	protected Observable<String> construct() {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> observer) {
				try {
					if (!observer.isUnsubscribed()) {
						// a real example would do work like a network call here
						//observer.onNext("Hello");
						observer.onNext(name + "!");
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		}).subscribeOn(Schedulers.io());
	}
	@Override
    protected Observable<String> resumeWithFallback() {
        return Observable.create((Observable.OnSubscribe<String>) observer -> {
            if (!observer.isUnsubscribed()){
                observer.onNext("Hello Guest!");
                observer.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

	public static void main(String[] args) {
		logger = Log4jUtils.initLog4j();
		String s = new HystrixObservableCommandHelloWorld("World").observe().toBlocking().single();
		logger.debug("s:" + s);
	}
}