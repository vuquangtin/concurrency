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
public class DistinctUntilChanged {

    public static void main(String[] args) {
        Observable.just("Apple", "Foo", "Book", "Emmmmmmm")
                .flatMap(i -> Observable.fromArray(i.split("")))
                .distinctUntilChanged()
                .doOnNext(System.out::print)
                .subscribe();
    }
}


