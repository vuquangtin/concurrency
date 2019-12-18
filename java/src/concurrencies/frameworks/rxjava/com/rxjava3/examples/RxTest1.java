package com.rxjava3.examples;

import io.reactivex.rxjava3.core.Flowable;
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
public class RxTest1 {
    public static void main(String[] args) {
        main0();
    }

    public static void main0() {

        Flowable.just("Hello word").subscribe(System.out:: println);
        Observable.create(new ObservableOnSubscribe<String>(){

            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Throwable {
                observableEmitter.onNext("aaaa");

            }
        }).subscribe(s -> {
            int a = 1/0;
        });

    }
}