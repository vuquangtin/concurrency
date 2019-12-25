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
public class From {

    public static void main(String[] args) {

        Observable.fromCallable(() -> "RxJava")
                .doOnNext(System.out::println)
                .doOnComplete(System.out::println)
                .subscribe();

        Observable.fromArray("James", "Tom", "Paul", "BMW")
                .doOnNext(System.out::println)
                .subscribe();
    }
}
