package com.rxjava3.creates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
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
public class IntervalDemo {
    public static void main(String[] args) {
//        Observable.interval(3, TimeUnit.SECONDS, Schedulers.trampoline()).subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long s) throws Exception {
//                System.out.println(s+"*");
//            }
//        });

        Observable.intervalRange(1, 4, 0,1, TimeUnit.SECONDS, Schedulers.trampoline()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long s) throws Exception {
                System.out.println(s+"*");
            }
        });
    }
}
