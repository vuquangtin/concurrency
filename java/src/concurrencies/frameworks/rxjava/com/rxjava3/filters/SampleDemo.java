package com.rxjava3.filters;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SampleDemo {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(1);
                Thread.sleep(1000);
                observableEmitter.onNext(2);
                Thread.sleep(500);
                observableEmitter.onNext(3);
                observableEmitter.onNext(4);
                Thread.sleep(501);
                observableEmitter.onNext(5);
                Thread.sleep(3000);
                observableEmitter.onNext(6);
                Thread.sleep(1000);
            }
        }).sample(2, TimeUnit.SECONDS)
                .subscribe(integer -> System.out.println(integer + ""));

        // throttleLast
        // throttleFirst
    }
}
