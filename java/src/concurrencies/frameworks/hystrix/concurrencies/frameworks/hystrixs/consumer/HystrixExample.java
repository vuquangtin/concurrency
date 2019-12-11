package concurrencies.frameworks.hystrixs.consumer;

import java.time.ZonedDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandMetrics.HealthCounts;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;

import rx.Observable;

public class HystrixExample {
	static class IdGeneratingCommand extends HystrixObservableCommand<String> {
		private final UnstableApplication app;

		public IdGeneratingCommand(HystrixObservableCommand.Setter setter,
				UnstableApplication app) {
			super(setter);
			this.app = app;
		}

		@Override
		protected Observable<String> construct() {
			return Observable.create(observer -> {
				try {
					if (!observer.isUnsubscribed()) {
						observer.onNext(app.generateId());
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			});
		}
	};

	public static void main(String[] args) throws Exception {
		UnstableApplication app = new UnstableApplication();

		HystrixObservableCommand.Setter setter = HystrixObservableCommand.Setter
				.withGroupKey(
						HystrixCommandGroupKey.Factory
								.asKey("unstableAppCmdGroup"))
				.andCommandPropertiesDefaults(
						HystrixCommandProperties
								.Setter()
								.withCircuitBreakerEnabled(true)
								.withCircuitBreakerErrorThresholdPercentage(50)
								.withCircuitBreakerSleepWindowInMilliseconds(
										1000)
								.withCircuitBreakerRequestVolumeThreshold(1));

		for (int i = 0; i < 10; i++) {
			CountDownLatch l = new CountDownLatch(1);
			IdGeneratingCommand cmd = new IdGeneratingCommand(setter, app);
			final HealthCounts healthCounts = cmd.getMetrics()
					.getHealthCounts();
			System.out
					.printf("circuit-breaker state is open: %s, %d errors of %d requests\n",
							cmd.isCircuitBreakerOpen(),
							healthCounts.getErrorCount(),
							healthCounts.getTotalRequests());
			Observable<String> observable = cmd.observe();
			observable
					.subscribe(
							s -> {
								System.out
										.printf("HystrixExample: id '%s' received at '%s'\n",
												s, ZonedDateTime.now());
							},
							t -> {
								System.err
										.printf("HystrixExample: error %s, circuit-breaker state is open: %s\n",
												t, cmd.isCircuitBreakerOpen());
							}, () -> {
								l.countDown();
							});
			l.await(4, TimeUnit.SECONDS);
		}
	}

}