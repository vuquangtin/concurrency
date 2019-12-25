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
public class SequenceEquals {

    public static void main(String[] args) {
        Observable.
                sequenceEqual(Observable.range(0, 5),
                        Observable.range(1, 5))
                .doOnSuccess(i -> System.out.println("Are they the same sequence? " + i))
                .subscribe();
    }
}
