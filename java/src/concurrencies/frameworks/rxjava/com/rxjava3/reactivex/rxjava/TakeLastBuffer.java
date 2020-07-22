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
public class TakeLastBuffer {

    public static void main(String[] args) throws InterruptedException {
        Observable.intervalRange(0, 10, 0, 100, TimeUnit.MILLISECONDS)
                .takeLast(3)
                .doOnNext(System.out::println)
                .subscribe();
        Thread.sleep(3_000);
    }
}

