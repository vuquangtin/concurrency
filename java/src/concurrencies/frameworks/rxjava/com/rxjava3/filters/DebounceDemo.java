package com.rxjava3.filters;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.concurrent.TimeUnit;

/**
 * @author ousiyuan
 * @date 2019/10/9
 * 发射一个数据后，随后线程睡眠超过设置的时间，数据才会真正发射出去，要不会被过滤
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
