package concurrencies.frameworks.hystrixs.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;

import concurrencies.utilities.Log4jUtils;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class NonDaemon {
	static Logger logger = Logger.getLogger(NonDaemon.class.getName());

	public static void main(String[] args) throws InterruptedException {
		logger = Log4jUtils.initLog4j();
		ExecutorService executor = Executors.newCachedThreadPool();
		// Observable.interval(1,
		// TimeUnit.SECONDS).doOnNext(System.out::println)
		// .subscribeOn(Schedulers.from(executor)).subscribe();

		ThreadPoolExecutor exec = new ThreadPoolExecutor(0, 64, 2,
				TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		exec.allowCoreThreadTimeOut(true);

		// rx.Scheduler s = Schedulers.from(exec);
		Observable<String> o1 = new DaemonHystrixCommand("One", 500)
				.toObservable().subscribeOn(Schedulers.from(executor));
		o1.subscribe();
		executor.shutdown();
	}

	private static final class DaemonHystrixCommand extends
			HystrixObservableCommand<String> {

		private final String name;
		private final int delayMillis;

		public DaemonHystrixCommand(String name, int delayMillis) {
			super(HystrixCommandGroupKey.Factory
					.asKey(DaemonHystrixCommand.class.getSimpleName()));
			this.name = name;
			this.delayMillis = delayMillis;
		}

		@Override
		protected Observable<String> construct() {
			return Observable.create(new Observable.OnSubscribe<String>() {
				@Override
				public void call(Subscriber<? super String> observer) {
					if (observer.isUnsubscribed()) {
						return;
					}
					for (int i = 0; i < 10; i++) {
						System.out.println("Begin [" + i + "] on thread "
								+ Thread.currentThread().getName());
						try {
							TimeUnit.MILLISECONDS.sleep(delayMillis);
						} catch (InterruptedException e) {
							observer.onError(e);
						}
						observer.onNext(name);

					}

					System.out.println("End " + name);
					observer.onCompleted();
				}
			});
		}
	}

}
