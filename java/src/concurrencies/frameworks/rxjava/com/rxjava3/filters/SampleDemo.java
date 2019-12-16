package com.rxjava3.filters;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

import java.util.concurrent.TimeUnit;

/**
 * @author ousiyuan
 * @date 2019/10/10
 * 周期采样，了解一下，好像没多大作用
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
