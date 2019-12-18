package com.rxjava3.conditions;

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
public class DefaultIfEmptyDemo {
    public static void main(String[] args) {
        Observable.empty().defaultIfEmpty(10).subscribe(num -> System.out.println(num+"*"));
    }
}
