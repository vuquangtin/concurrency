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
public class Delay {

    public static void main(String[] args) throws InterruptedException {
        Observable.range(1, 10)
                .delay(Observable.empty().delay(1, TimeUnit.SECONDS),
                        i -> Observable.empty().delay(i * i * 100, TimeUnit.MILLISECONDS))
                .doOnNext(System.out::println)
                .subscribe();
        Thread.sleep(10_000);
    }
}
