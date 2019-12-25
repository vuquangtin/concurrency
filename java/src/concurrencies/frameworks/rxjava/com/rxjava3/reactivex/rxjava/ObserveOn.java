package com.rxjava3.reactivex.rxjava;

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
public class ObserveOn {

    public static void main(String[] args) throws InterruptedException {
        Observable.just(1)
                .observeOn(Schedulers.io())
                .doOnNext(ObserveOn::threadInfo)

                .observeOn(Schedulers.io())
                .map(i -> 10 * i)
                .doOnNext(ObserveOn::threadInfo)

                .observeOn(Schedulers.io())
                .map(i -> 10 * i)
                .doOnNext(ObserveOn::threadInfo)

                .observeOn(Schedulers.io())
                .map(i -> 10 * i)
                .doOnNext(ObserveOn::threadInfo)

                .observeOn(Schedulers.io())
                .map(i -> 10 * i)
                .doOnNext(ObserveOn::threadInfo)

                .observeOn(Schedulers.io())
                .map(i -> 10 * i)
                .doOnNext(ObserveOn::threadInfo)

                .observeOn(Schedulers.io())
                .map(i -> 10 * i)
                .doOnNext(ObserveOn::threadInfo)

                .doOnSubscribe(ObserveOn::threadInfo)
                .subscribeOn(Schedulers.newThread())
                .subscribe();

        Thread.sleep(1_000);
    }

    static void threadInfo(Object message) {
        System.out.println(Thread.currentThread().getName() + ": " + message);
    }
}