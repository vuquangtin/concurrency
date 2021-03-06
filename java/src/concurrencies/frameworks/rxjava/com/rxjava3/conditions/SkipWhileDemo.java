package com.rxjava3.conditions;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Predicate;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SkipWhileDemo {
    public static void main(String[] args) {
        Observable.just(1, 2, 3, 4, 5).skipWhile(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Throwable {
                return integer != 3;
            }
        }).subscribe(num -> System.out.println(num +"*"));
    }
}
