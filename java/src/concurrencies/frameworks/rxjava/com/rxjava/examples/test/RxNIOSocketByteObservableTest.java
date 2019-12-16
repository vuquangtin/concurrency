package com.rxjava.examples.test;

import rx.Observable;
import rx.schedulers.Schedulers;

import com.rxjava.examples.RxNIOSocketByteObservable;

/**
 * Created by mbk on 5/24/16.
 */
public class RxNIOSocketByteObservableTest {

    public static void main(String[] args) throws Exception {
        Observable<byte[]> observable = new RxNIOSocketByteObservable().toObservable();

        observable
                .subscribeOn(Schedulers.computation())
                .map(bytes -> new String(bytes))
                .toBlocking()
                .subscribe(System.out::println);

    }
}