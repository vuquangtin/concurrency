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
public class IgnoreElements {

    public static void main(String[] args) throws InterruptedException {
        Observable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS)
                .ignoreElements()
                .doOnSubscribe(i -> System.out.println("Subscribed"))
                .doOnComplete(() -> System.out.println("Completed"))
                .subscribe();
        Thread.sleep(5_000);
    }
}


