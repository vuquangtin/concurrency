package com.rxjava3.creates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
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
