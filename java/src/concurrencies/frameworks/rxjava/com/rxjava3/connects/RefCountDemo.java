package com.rxjava3.connects;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

/**
 * @author ousiyuan
 * @date 2019/11/7
 */
public class RefCountDemo {
    public static void main(String[] args) {
        ConnectableObservable connectableObservable = Observable.just(1,2,3,4).publish();
        connectableObservable.subscribe(num -> System.out.println(num +"*"));
        connectableObservable.subscribe(num -> System.out.println(num +"+"));

        connectableObservable.refCount().subscribe(num -> System.out.println(num+"-"));
    }
}
