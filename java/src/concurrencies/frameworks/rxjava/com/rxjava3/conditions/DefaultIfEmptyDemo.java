package com.rxjava3.conditions;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author ousiyuan
 * @date 2019/11/7
 */
public class DefaultIfEmptyDemo {
    public static void main(String[] args) {
        Observable.empty().defaultIfEmpty(10).subscribe(num -> System.out.println(num+"*"));
    }
}
