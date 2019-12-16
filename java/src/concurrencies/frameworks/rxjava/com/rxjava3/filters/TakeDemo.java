package com.rxjava3.filters;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author ousiyuan
 * @date 2019/10/10
 */
public class TakeDemo {
    public static void main(String[] args) {
        Observable.just(1,2,3,4,5,6)
                .take(3)
                .subscribe(integer -> System.out.println(integer+"*"));
        //.take(3,TimeUnit.SECONDS)

        Observable.just(1,2,3,4,5,6)
                .takeLast(3)
                .subscribe(integer -> System.out.println(integer+"*"));
        //.takeLast(3,TimeUnit.SECONDS)
    }
}
