package com.rxjava3.assists;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

import java.util.concurrent.TimeUnit;

/**
 * @author ousiyuan
 * @date 2019/11/5
 */
public class TimeoutDemo {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(1);
                Thread.sleep(1000);
                observableEmitter.onNext(2);
                Thread.sleep(1000);
                observableEmitter.onNext(3);
                Thread.sleep(3000);
                observableEmitter.onNext(4);
            }
        }).timeout(2000, TimeUnit.MILLISECONDS).subscribe(num -> System.out.println(num+"*"), throwable -> {System.out.println(throwable.getMessage());});
    }
}
