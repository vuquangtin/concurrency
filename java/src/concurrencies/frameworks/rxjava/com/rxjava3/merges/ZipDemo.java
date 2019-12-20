package com.rxjava3.merges;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.BiFunction;

import java.util.Arrays;
import java.util.List;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ZipDemo {
    public static void main(String[] args) {
        Observable observable1 = Observable.just(1,2,3);
        List<Integer> list = Arrays.asList(4,5,6);
        observable1.zipWith(list, new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(Integer o, Integer o2) throws Throwable {
                return o+":"+o2;
            }
        }).subscribe(num -> System.out.println(num+""));

        Observable observable2 = Observable.just(7,8,9);
        observable1.zipWith(observable2, (num1, num2) -> num1+":"+num2)
        .subscribe(num -> System.out.println(num+""));
    }
}
