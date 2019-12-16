package com.rxjava3.conditions;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Predicate;

/**
 * @author ousiyuan
 * @date 2019/11/7
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
