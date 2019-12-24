package com.rxjava3.reactivex.io.singles;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Asyn {

	public static void main(String[] args) {
		Observable.defer(() -> Observable.just(1,2,3,4,5,6))
       // .flatMap(users -> Observable.from(users))
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.trampoline())
        .subscribe(user -> System.out.println(String.format("User: %s", user.toString())));

	}
}
