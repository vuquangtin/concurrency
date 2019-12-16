package com.rxjava3.merges;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * @author ousiyuan
 * @date 2019/10/23
 */
public class MergeDemo {
    public static void main(String[] args) {
        Observable observable1 = Observable.intervalRange(5, 3, 500, 1000, TimeUnit.MILLISECONDS);
        Observable observable2 = Observable.intervalRange(1, 4, 0, 1000, TimeUnit.MILLISECONDS, Schedulers.trampoline());
        Observable.merge(observable1, observable2).subscribe(num -> System.out.println(num+"*"));
    }
}
