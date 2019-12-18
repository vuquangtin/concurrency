package com.rxjava3.reactivex;

import java.util.concurrent.CompletableFuture;

import reactor.core.publisher.Flux;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

/**
 * Wraps or converts another reactive type to the target reactive type.
 * 
 * The following combinations are available in the various reactive types with
 * the following signature pattern: targetType.from{sourceType}()
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FromReactiveType {

	public static void main(String[] args) {
//		Flux<Integer> reactorFlux = Flux.fromCompletionStage(CompletableFuture
//				.<Integer> completedFuture(1));
//
//		Observable<Integer> observable = Observable.fromPublisher(reactorFlux);
//
//		observable.subscribe(item -> System.out.println(item),
//				error -> error.printStackTrace(),
//				() -> System.out.println("Done"));

	}

}
