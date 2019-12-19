package com.rxjava3.assists;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class DelayDemo {
    public static void main(String[] args) {
        // 每个数据发送都延迟
        Observable.just(1,2,3).delay(2000, TimeUnit.MILLISECONDS, Schedulers.trampoline()).subscribe(num -> System.out.println(num+""));
    }
}
