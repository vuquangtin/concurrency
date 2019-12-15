package com.rxjava3.examples;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * @Description
 * @Date 2019/11/20 14:05
 * @Author mxz
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