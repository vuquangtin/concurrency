package com.rxjava3.reactivex;

import io.reactivex.rxjava3.core.Observable;

/**
 * 
 Calls an user-provided java.util.concurrent.Callable when a consumer
 * subscribes to the reactive type so that the Callable can generate the actual
 * reactive instance to relay signals from towards the consumer. defer allows:
 * <ul>
 * <li>associating a per-consumer state with such generated reactive instances,</li>
 * <li>allows executing side-effects before an actual/generated reactive
 * instance gets subscribed to,</li>
 * <li>turn hot sources (i.e., Subjects and Processors) into cold sources by
 * basically making those hot sources not exist until a consumer subscribes.</li>
 * </ul>
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class DeferExample {

	public static void main(String[] args) {
		Observable<Long> observable = Observable.defer(() -> {
			long time = System.currentTimeMillis();
			return Observable.just(time);
		});

		observable.subscribe(time -> System.out.println(time));

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		observable.subscribe(time -> System.out.println(time));

	}

}
