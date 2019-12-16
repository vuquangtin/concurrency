package com.rxjava3.conditions;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * @author ousiyuan
 * @date 2019/11/7
 */
public class TakeUntilDemo {
    public static void main(String[] args) {
        Observable observable1 = Observable.intervalRange(1, 4, 0,1, TimeUnit.SECONDS, Schedulers.trampoline());
        Observable observable2 = Observable.intervalRange(10, 4, 2,1, TimeUnit.SECONDS, Schedulers.newThread());
//        observable1.takeUntil(observable2).subscribe(num -> System.out.println(num+"*"));


        Observable.just(1,2,3,4).takeUntil(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Throwable {
                return integer == 3;
            }
        }).subscribe(num -> System.out.println(num+"*"));
    }
}
