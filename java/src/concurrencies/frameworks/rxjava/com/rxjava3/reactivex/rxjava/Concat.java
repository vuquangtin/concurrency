package com.rxjava3.reactivex.rxjava;

import java.util.concurrent.TimeUnit;

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
public class Concat {

    public static void main(String[] args) throws InterruptedException {
        Observable.fromArray("James", "Tom", "Paul", "BMW")
                .concatWith(Observable.interval(1_000, TimeUnit.MILLISECONDS).map(String::valueOf))
                .doOnNext(System.out::println)
                .subscribe();
        Thread.sleep(4_000);
    }
}