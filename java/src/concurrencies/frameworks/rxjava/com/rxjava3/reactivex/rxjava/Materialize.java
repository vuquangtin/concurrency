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
public class Materialize {

    public static void main(String[] args) throws InterruptedException {
        Observable.intervalRange(0, 4, 0, 1, TimeUnit.SECONDS)
                .materialize()
                .doOnNext(n -> System.out.println("value: " + n.getValue() +
                        ", onNext? " + n.isOnNext() + ", onComplete?" + n.isOnComplete()))
                .subscribe();
        Thread.sleep(5_000);
    }
}


