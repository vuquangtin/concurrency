package com.rxjava3.reactivex.rxjava;

import io.reactivex.rxjava3.core.Observable;

import java.util.Random;
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
public class TimeInterval {

    public static void main(String[] args) {
        Observable.fromArray("James", "Tom", "Paul", "BMW")
                .doOnNext(i -> Thread.sleep(new Random().nextInt(2_000)))
                .timeInterval(TimeUnit.MILLISECONDS)
                .doOnNext(System.out::println)
                .subscribe();
    }
}


