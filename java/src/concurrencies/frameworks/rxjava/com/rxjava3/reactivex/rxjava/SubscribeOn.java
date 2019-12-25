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
public class SubscribeOn {

    public static void main(String[] args) throws InterruptedException {
        Observable.just(1)
                .doOnSubscribe(ObserveOn::threadInfo)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
        Thread.sleep(1_000);
    }
}

