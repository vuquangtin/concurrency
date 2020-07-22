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
public class Map {

    public static void main(String[] args) throws InterruptedException {
        Observable.interval(500, TimeUnit.MILLISECONDS)
                .map(i -> 10 * i)
                .doOnNext(System.out::println)
                .subscribe();
        Thread.sleep(5_000);
    }
}

