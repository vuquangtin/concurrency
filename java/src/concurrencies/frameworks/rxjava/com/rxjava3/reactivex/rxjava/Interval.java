package com.rxjava3.reactivex.rxjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Interval {

    public static void main(String[] args) throws InterruptedException {
    	ExecutorService executor =Executors.newCachedThreadPool();
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnNext(System.out::println)
                .subscribeOn(Schedulers.from(executor)).subscribe();
        //Schedulers.from(executor);
        //Thread.sleep(5_000);
    }
}


