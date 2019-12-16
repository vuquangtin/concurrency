package com.rxjava3.conditions;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Predicate;

/**
 * @author ousiyuan
 * @date 2019/11/6
 */
public class AllDemo {
    public static void main(String[] args) {
        Observable.just(1, 2, 3, 4).all(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Throwable {
                return integer > 0;
            }
        }).subscribe(num -> System.out.println(num+"*"));
    }
}
