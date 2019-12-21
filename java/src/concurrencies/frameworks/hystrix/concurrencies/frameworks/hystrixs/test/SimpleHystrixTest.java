package concurrencies.frameworks.hystrixs.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.junit.Test;

import rx.Observable;
import rx.Observer;
import rx.observables.BlockingObservable;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

/**
 * Created by pc on 2017/11/19.
 */
public class SimpleHystrixTest {

	@Test
	public void testCreateSimpleCommand() throws Exception {
		HystrixCommand<String> testSimpleCommand = SimpleHystrix
				.createSimpleCommand("testSimpleCommand");
		String result = testSimpleCommand.execute();
		System.out.println(result);
	}

	@Test
	public void testCreateSimpleCommandWithAsyncExecution()
			throws InterruptedException, ExecutionException, TimeoutException {
		HystrixCommand<String> testSimpleCommand = SimpleHystrix
				.createSimpleCommand("testSimpleCommand");
		Future<String> future = testSimpleCommand.queue();
		String result = future.get(500, TimeUnit.MILLISECONDS);
		System.out.println(result);
	}

	@Test
	public void testCreateSimpleObservableCommand() throws Exception {
		HystrixObservableCommand<String> testSimpleCommandObservable = SimpleHystrix
				.createSimpleObservableCommand("testSimpleCommandObservable");
		Observable<String> observe = testSimpleCommandObservable.observe();
		observe.subscribe(new Observer<String>() {
			@Override
			public void onCompleted() {
				System.out.println("observer completed");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("observer error");
			}

			@Override
			public void onNext(String s) {
				System.out.println("observer onNext " + s);
			}
		});
	}

	@Test
	public void testCreateSimpleObservableCommandWithFuture() throws Exception {
		HystrixObservableCommand<String> testSimpleCommandObservable = SimpleHystrix
				.createSimpleObservableCommand("testSimpleCommandObservable");
		BlockingObservable<String> blocking = testSimpleCommandObservable
				.observe().toBlocking();
		Future<String> future = blocking.toFuture();
		String result = future.get();
		System.out.println(result);
	}

	@Test
	public void testCreateSingleObservableCommandWithFuture() throws Exception {
		HystrixObservableCommand<String> testSimpleCommandObservable = SimpleHystrix
				.createSingleObservableCommand("testSingleCommandObservable");
		BlockingObservable<String> blocking = testSimpleCommandObservable
				.observe().toBlocking();
		Future<String> future = blocking.toFuture();
		String result = future.get();
		System.out.println(result);
	}

	@Test
	public void testHotObservable() throws InterruptedException {
		HystrixObservableCommand<String> observableCommand = SimpleHystrix
				.createSimpleObservableCommand("testSimpleCommandObservable");
		Observable<String> observe = observableCommand.observe();
		Thread.sleep(1000);
		observe.subscribe(new Observer<String>() {
			@Override
			public void onCompleted() {
				System.out.println("observer completed @ "
						+ System.currentTimeMillis());
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("observer error @ "
						+ System.currentTimeMillis());
			}

			@Override
			public void onNext(String s) {
				System.out.println("observer @ " + System.currentTimeMillis()
						+ " onNext with " + s);
			}
		});
	}

	@Test
	public void testColdObservable() throws InterruptedException {
		HystrixObservableCommand<String> observableCommand = SimpleHystrix
				.createSimpleObservableCommand("testSimpleCommandObservable");
		Observable<String> observe = observableCommand.toObservable();
		Thread.sleep(1000);
		observe.subscribe(new Observer<String>() {
			@Override
			public void onCompleted() {
				System.out.println("observer completed @ "
						+ System.currentTimeMillis());
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("observer error @ "
						+ System.currentTimeMillis());
			}

			@Override
			public void onNext(String s) {
				System.out.println("observer @ " + System.currentTimeMillis()
						+ " onNext with " + s);
			}
		});
	}

	@Test
	public void testCreateSimpleFallBack() throws Exception {
		HystrixCommand<String> hystrixCommand = SimpleHystrix
				.createSimpleFallBack("testCreateSimpleFallBack");
		String result = hystrixCommand.execute();
		Assert.assertEquals("this is fallback", result);
	}

	@Test
	public void testCreateSimpleObservableFallBack() throws Exception {
		HystrixObservableCommand<String> testCreateSimpleObservableFallBack = SimpleHystrix
				.createSimpleObservableFallBack("testCreateSimpleObservableFallBack");
		Observable<String> observe = testCreateSimpleObservableFallBack
				.observe();
		observe.subscribe(new Observer<String>() {
			@Override
			public void onCompleted() {
				System.out.println("observer completed");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println("observer error");
			}

			@Override
			public void onNext(String s) {
				System.out.println("observer onNext " + s);
			}
		});
	}
}