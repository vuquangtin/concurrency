package concurrencies.frameworks.hystrixs;

import org.springframework.stereotype.Service;

import rx.Observable;
import rx.Subscriber;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;

@Service
public class ObservableUserService {
	/**
	 * EAGER參數表示使用observe()方式執行
	 */
	@HystrixCommand(observableExecutionMode = ObservableExecutionMode.EAGER, fallbackMethod = "observFailed")
	// 使用observe()執行方式
	public Observable<String> getUserById(final Long id) {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				try {
					if (!subscriber.isUnsubscribed()) {
						subscriber.onNext("張三的ID:");
						int i = 1 / 0; // 拋異常，模擬服務降級
						subscriber.onNext(String.valueOf(id));
						subscriber.onCompleted();
					}
				} catch (Exception e) {
					subscriber.onError(e);
				}
			}
		});
	}

	private String observFailed(Long id) {
		return "observFailed---->" + id;
	}

	/**
	 * LAZY參數表示使用toObservable()方式執行
	 */
	@HystrixCommand(observableExecutionMode = ObservableExecutionMode.LAZY, fallbackMethod = "toObserbableError")
	// 表示使用toObservable()執行方式
	public Observable<String> getUserByName(final String name) {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				try {
					if (!subscriber.isUnsubscribed()) {
						subscriber.onNext("找到");
						subscriber.onNext(name);
						int i = 1 / 0; // //拋異常，模擬服務降級
						subscriber.onNext("了");
						subscriber.onCompleted();
					}
				} catch (Exception e) {
					subscriber.onError(e);
				}
			}
		});
	}

	private String toObserbableError(String name) {
		return "toObserbableError--->" + name;
	}

}