package com.rxjava3.conditions;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SequenceEqualDemo {
    public static void main(String[] args) {
        Observable observable1 = Observable.intervalRange(1, 4, 0,1, TimeUnit.SECONDS, Schedulers.trampoline());
        Observable observable2 = Observable.intervalRange(1, 4, 2,1, TimeUnit.SECONDS, Schedulers.trampoline());
        Observable.sequenceEqual(observable1, observable2).subscribe(num -> System.out.println(num+"*"));
    }
}
