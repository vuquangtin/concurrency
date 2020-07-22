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
public class Join {

    public static void main(String[] args) throws InterruptedException {
        Observable.interval(500, TimeUnit.MILLISECONDS).debounce(100, TimeUnit.MILLISECONDS)
                .join(Observable.interval(500, TimeUnit.MILLISECONDS).delay(250, TimeUnit.MILLISECONDS),
                        a -> Observable.interval(500, TimeUnit.MILLISECONDS),
                        a -> Observable.interval(500, TimeUnit.MILLISECONDS).delay(500, TimeUnit.MILLISECONDS),
                        (a, b) -> a + "-" + b)
                .doOnNext(System.out::println)
                .subscribe();
        Thread.sleep(5_000);
    }
}


