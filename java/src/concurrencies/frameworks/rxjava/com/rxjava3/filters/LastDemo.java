package com.rxjava3.filters;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author ousiyuan
 * @date 2019/10/10
 */
public class LastDemo {
    public static void main(String[] args) {
        Observable.just(1,2,3)
                .lastElement()
                .subscribe(integer -> System.out.println(integer+"*"));
    }
}
