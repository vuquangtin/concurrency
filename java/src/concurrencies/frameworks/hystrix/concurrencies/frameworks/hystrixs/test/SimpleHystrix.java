package concurrencies.frameworks.hystrixs.test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Observer;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func2;
import rx.observables.SyncOnSubscribe;

import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SimpleHystrix {

	public static HystrixCommand<String> createSimpleCommand(String commandName) {
		return new HystrixCommand<String>(
				HystrixCommandGroupKey.Factory.asKey(commandName)) {

			@Override
			protected String run() throws Exception {
				return "Hello " + getCommandGroup().name() + " @ "
						+ System.currentTimeMillis();
			}
		};
	}

	public static HystrixObservableCommand<String> createSimpleObservableCommand(
			String commandName) {
		return new HystrixObservableCommand<String>(
				HystrixCommandGroupKey.Factory.asKey(commandName)) {
			@Override
			protected Observable<String> construct() {
				return Observable.create(createSyncOnSubscribe());
			}
		};
	}

	private static SyncOnSubscribe<Integer, String> createSyncOnSubscribe() {
		return SyncOnSubscribe.createStateful(new Func0<Integer>() {
			@Override
			public Integer call() {
				return 10;
			}
		}, new Func2<Integer, Observer<? super String>, Integer>() {
			@Override
			public Integer call(Integer integer,
					Observer<? super String> observer) {
				if (integer <= 0) {
					observer.onCompleted();
					return -1;
				}
				observer.onNext("subscribe on " + integer + " @ "
						+ System.currentTimeMillis());
				return integer - 1;
			}
		});
	}

	public static HystrixObservableCommand<String> createSingleObservableCommand(
			String commandName) {
		return new HystrixObservableCommand<String>(
				HystrixCommandGroupKey.Factory.asKey(commandName)) {
			@Override
			protected Observable<String> construct() {
				return Observable.create(createSingleSyncOnSubscribe());
			}
		};
	}

	private static SyncOnSubscribe<Integer, String> createSingleSyncOnSubscribe() {
		return SyncOnSubscribe.createSingleState(new Func0<Integer>() {
			@Override
			public Integer call() {
				return 0;
			}
		}, new Action2<Integer, Observer<? super String>>() {
			@Override
			public void call(Integer integer, Observer<? super String> observer) {
				observer.onNext("single subscribe");
				observer.onCompleted();
			}
		});
	}

	public static HystrixCommand<String> createSimpleFallBack(String commandName) {
		return new HystrixCommand<String>(
				HystrixCommandGroupKey.Factory.asKey(commandName)) {

			@Override
			protected String run() throws Exception {
				throw new TimeoutException("I throw");
			}

			@Override
			protected String getFallback() {
				System.out.println(getExecutionException().getMessage());
				System.out.println(getFailedExecutionException().getMessage());
				return "this is fallback";
			}
		};
	}

	public static HystrixObservableCommand<String> createSimpleObservableFallBack(
			String commandName) {
		return new HystrixObservableCommand<String>(
				HystrixCommandGroupKey.Factory.asKey(commandName)) {

			@Override
			protected Observable<String> construct() {

				return Observable
						.create(createSyncOnSubscribeRandomException());
			}

			@Override
			protected Observable<String> resumeWithFallback() {
				return Observable.create(createFallBackSyncOnSubscribe());
			}
		};
	}

	private static SyncOnSubscribe<Integer, String> createFallBackSyncOnSubscribe() {
		return SyncOnSubscribe.createSingleState(new Func0<Integer>() {
			@Override
			public Integer call() {
				return 0;
			}
		}, new Action2<Integer, Observer<? super String>>() {
			@Override
			public void call(Integer integer, Observer<? super String> observer) {
				observer.onNext("this is fallback");
				observer.onCompleted();
			}
		});
	}

	private static SyncOnSubscribe<Integer, String> createSyncOnSubscribeRandomException() {
		return SyncOnSubscribe.createStateful(new Func0<Integer>() {
			@Override
			public Integer call() {
				return 10;
			}
		}, new Func2<Integer, Observer<? super String>, Integer>() {
			private Random random = new Random();

			@Override
			public Integer call(Integer integer,
					Observer<? super String> observer) {
				int ri = random.nextInt(2);
				if (ri == 1) {
					throw new RuntimeException("I throw");
				}

				if (integer <= 0) {
					observer.onCompleted();
					return -1;
				}
				observer.onNext("subscribe on " + integer + " @ "
						+ System.currentTimeMillis());
				return integer - 1;
			}
		});
	}
}