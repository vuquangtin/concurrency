package com.rxjava3.reactivex.rxjava;

import io.reactivex.rxjava3.core.Observable;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Defer {

    public static void main(String[] args) throws InterruptedException {
        Observable<String> observable = Observable.fromArray("James", "Tom", "Paul", "BMW");
        Observable<String> deferObservable = Observable.defer(() -> observable)
                .doOnNext(System.out::println);
        Thread.sleep(1_000);
        deferObservable.subscribe();
    }
}


