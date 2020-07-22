package concurrencies.frameworks.hystrixs.async;

import rx.Observable;
import rx.Subscriber;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class UserHystrixObservableCommand extends HystrixObservableCommand<User> {

	public UserHystrixObservableCommand() {
		super(HystrixCommandGroupKey.Factory.asKey("demo-key"));
	}

	@Override
	protected Observable<User> construct() {

		return Observable.create(new Observable.OnSubscribe<User>() {
			@Override
			public void call(Subscriber<? super User> subscriber) {
				try {
					Thread.sleep(2000);
					User user = new User("demo", "123456");
					subscriber.onNext(user);
					subscriber.onCompleted();
				} catch (Exception ex) {
					subscriber.onError(ex);
				}
			}
		});
	}

	@Override
	protected Observable<User> resumeWithFallback() {

		return Observable.create(new Observable.OnSubscribe<User>() {
			@Override
			public void call(Subscriber<? super User> subscriber) {
				try {
					User user = new User("fallback", "123456");
					subscriber.onNext(user);
					subscriber.onCompleted();
				} catch (Exception ex) {
					subscriber.onError(ex);
				}
			}
		});
	}
}
