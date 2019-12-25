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
public class Window {

    public static void main(String[] args) throws InterruptedException {
        Observable.interval(200, TimeUnit.MILLISECONDS)
                .window(400)
                .map(i -> i.doOnNext(System.out::println).subscribe())
                .subscribe();
        Thread.sleep(5_000);
    }
}
