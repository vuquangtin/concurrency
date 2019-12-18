package com.rxjava3.filters;

import io.reactivex.rxjava3.core.Observable;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FirstDemo {
    public static void main(String[] args) {
        Observable.just(1,2,3)
                .firstElement()
                .subscribe(integer -> System.out.println(integer+"*"));

        Observable.empty()
                .first(2)
                .subscribe(integer -> System.out.println(integer+"*"));

        // .firstElement
        //.firstOrError
    }
}
