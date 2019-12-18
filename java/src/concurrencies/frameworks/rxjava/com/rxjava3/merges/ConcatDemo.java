package com.rxjava3.merges;

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
public class ConcatDemo {
    public static void main(String[] args) {
        Observable observable1 = Observable.just(1,2,3,4);
        Observable observable2 = Observable.just(-1,-2,-3,-4);

        observable1.concatWith(observable2).subscribe(num -> System.out.println(num+""));
    }
}
