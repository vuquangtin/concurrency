package com.rxjava3.creates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * @author ousiyuan
 * @date 2019/9/30
 * defer 等待观察者订阅这个observable是，会调用自己返回的一个Observable
 */
public class DeferDemo {
    public static int num = 4;
    public static void main(String[] args) {
        Observable<Integer> observable = Observable.defer(() ->
                new Observable<Integer>() {
                    @Override
                    protected void subscribeActual(Observer<? super Integer> observer) {
                        observer.onNext(num);
                    }
                });
//        Observable observable = Observable.defer(new Supplier<ObservableSource<Integer>>() {
//            @Override
//            public ObservableSource<Integer> get() throws Throwable {
//                return new Observable<Integer>() {
//                    @Override
//                    protected void subscribeActual(Observer<? super Integer> observer) {
//                        observer.onNext(num);
//                    }
//                };
//            }
//        });

        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer num) throws Exception {
                System.out.println(num+"*");
            }
        });
    }
}
