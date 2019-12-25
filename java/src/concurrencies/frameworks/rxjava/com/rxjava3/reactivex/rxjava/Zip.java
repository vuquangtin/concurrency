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
public class Zip {

    public static void main(String[] args) {
        Observable.fromArray("James", "Tom", "Paul", "BMW")
                .flatMap(i -> Observable.fromArray(i.split("")))
                .sorted()
                .distinct()
                .zipWith(Observable.range(1, 100), (a, b) -> b + ". " + a)
                .doOnNext(System.out::println)
                .subscribe();
    }
}


