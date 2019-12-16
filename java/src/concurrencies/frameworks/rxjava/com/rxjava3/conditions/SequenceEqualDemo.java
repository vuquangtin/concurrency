package com.rxjava3.conditions;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * @author ousiyuan
 * @date 2019/11/7
 */
public class SequenceEqualDemo {
    public static void main(String[] args) {
        Observable observable1 = Observable.intervalRange(1, 4, 0,1, TimeUnit.SECONDS, Schedulers.trampoline());
        Observable observable2 = Observable.intervalRange(1, 4, 2,1, TimeUnit.SECONDS, Schedulers.trampoline());
        Observable.sequenceEqual(observable1, observable2).subscribe(num -> System.out.println(num+"*"));
    }
}
