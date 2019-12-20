package com.rxjava3.filters;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.concurrent.TimeUnit;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class DebounceDemo {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(1);
                Thread.sleep(200);
                observableEmitter.onNext(2);
                Thread.sleep(300);
                observableEmitter.onNext(3);
                Thread.sleep(302);
                observableEmitter.onNext(4);
                Thread.sleep(100);
                observableEmitter.onNext(5);
                Thread.sleep(420);
                observableEmitter.onNext(6);//
            }
        }).debounce(300, TimeUnit.MILLISECONDS) // ThrottleWithTimeout与其功能一样
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {
                        System.out.println(s + "*");
                    }
                });
    }
}
