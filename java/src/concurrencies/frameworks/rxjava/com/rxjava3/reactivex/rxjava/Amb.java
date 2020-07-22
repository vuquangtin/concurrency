package com.rxjava3.reactivex.rxjava;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Amb {

    public static void main(String[] args) {
        Observable.range(1,5)
                .delay(1, TimeUnit.MILLISECONDS)
                .ambWith(Observable.range(10,5))
                .doOnNext(System.out::println)
                .subscribe();
    }
}


