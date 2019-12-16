package com.rxjava3.filters;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author ousiyuan
 * @date 2019/10/10
 */
public class SkipDemo {
    public static void main(String[] args) {
        Observable.just(1,2,3,4,5,6)
                .skip(3)
                .subscribe(integer -> System.out.println(integer+"*"));
        //.skip(3,TimeUnit.SECONDS)

        Observable.just(1,2,3,4,5,6)
                .skipLast(3)
                .subscribe(integer -> System.out.println(integer+"*"));
        //.skipLast(3,TimeUnit.SECONDS)
    }
}
