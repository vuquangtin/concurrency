package com.rxjava3.conditions;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * @author ousiyuan
 * @date 2019/11/6
 * 代码不行
 */
public class AmbDemo {
    public static void main(String[] args) {
        Observable observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                Thread.sleep(1000);
                observableEmitter.onNext(1);
                Thread.sleep(1000);
                observableEmitter.onNext(2);
                Thread.sleep(1000);
                observableEmitter.onNext(3);
                Thread.sleep(1000);
                observableEmitter.onNext(4);
            }
        });
        Observable observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(7);
                Thread.sleep(1000);
                observableEmitter.onNext(8);
            }
        });

        Observable.ambArray(observable1, observable2).subscribe(num -> System.out.println(num+"*"));
    }
}
