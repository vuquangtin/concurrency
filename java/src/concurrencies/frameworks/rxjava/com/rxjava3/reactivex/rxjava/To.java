package com.rxjava3.reactivex.rxjava;

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
public class To {

    public static void main(String[] args) {
        Observable.range(0, 10)
                .to(Observable::count)
                .doOnSuccess(System.out::println)
                .subscribe();
    }
}

