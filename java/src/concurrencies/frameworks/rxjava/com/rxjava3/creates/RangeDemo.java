package com.rxjava3.creates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class RangeDemo {
    public static void main(String[] args) {
        Observable.range(2, 4).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer num) throws Exception {
                System.out.println(num+"*");
            }
        });
    }
}
