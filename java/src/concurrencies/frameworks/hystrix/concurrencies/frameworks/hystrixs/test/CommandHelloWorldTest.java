package concurrencies.frameworks.hystrixs.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import concurrencies.frameworks.hystrixs.CommandHelloWorld;

/**
 * Created by lisen on 2017/12/15.
 *
 */
public class CommandHelloWorldTest {

	/**
	 * 测试同步执行
	 */
	@Test
	public void testSynchronous() {
		System.out.println(new CommandHelloWorld("World").execute());
	}

	/**
	 * 测试异步执行
	 */
	@Test
	public void testAsynchronous() throws ExecutionException,
			InterruptedException {
		Future<String> fWorld = new CommandHelloWorld("World").queue();
		System.out.println(fWorld.get()); // 一步执行用get()来获取结果
	}

	/**
	 * 虽然HystrixCommand具备了observe()和toObservable()的功能，但是它的实现有一定的局限性，
	 * 它返回的Observable只能发射一次数据，所以Hystrix还提供了HystrixObservableCommand,
	 * 通过它实现的命令可以获取能发多次的Observable
	 */
	@Test
	public void testObserve() {
		/**
		 * 返回的是Hot Observable,HotObservable，不论 “事件源” 是否有“订阅者”
		 * 都会在创建后对事件进行发布。所以对于Hot Observable的每一个“订阅者”都有
		 * 可能从“事件源”的中途开始的，并可能只是看到了整个操作的局部过程
		 */
		// blocking
		Observable<String> ho = new CommandHelloWorld("World").observe();
		// System.out.println(ho.toBlocking().single());

		// non-blockking
		// - this is a verbose anonymous inner-class approach and doesn't do
		// assertions
		ho.subscribe(new Observer<String>() {
			@Override
			public void onCompleted() {
				System.out.println("==============onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onNext(String s) {
				System.out.println("=========onNext: " + s);
			}
		});

		ho.subscribe(new Action1<String>() {
			@Override
			public void call(String s) {
				System.out.println("==================call:" + s);
			}
		});
	}

	@Test
	public void testToObservable() {
		/**
		 * Cold Observable在没有 “订阅者” 的时候并不会发布时间， 而是进行等待，知道有 “订阅者” 之后才发布事件，所以对于
		 * Cold Observable的订阅者，它可以保证从一开始看到整个操作的全部过程。
		 */
		Observable<String> co = new CommandHelloWorld("World").toObservable();
		System.out.println(co.toBlocking().single());
	}

}