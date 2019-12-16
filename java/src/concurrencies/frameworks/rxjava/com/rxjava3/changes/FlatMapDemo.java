package com.rxjava3.changes;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ousiyuan
 * @date 2019/9/30
 */
public class FlatMapDemo {
    public static void main(String[] args) {
        Observable.just(1,2,3).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Throwable {
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < integer; i++){
                    list.add(integer);
                }
                return  Observable.fromIterable(list).map(num -> num+"+");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s+"*");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("完成");
            }
        });
    }
}
