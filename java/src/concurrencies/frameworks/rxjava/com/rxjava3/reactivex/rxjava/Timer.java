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
public class Timer {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Time start");
        Observable
                .timer(500, TimeUnit.MILLISECONDS)
                .doOnNext(i -> System.out.println("Time out"))
                .subscribe();
        Thread.sleep(1_000);
    }
}


