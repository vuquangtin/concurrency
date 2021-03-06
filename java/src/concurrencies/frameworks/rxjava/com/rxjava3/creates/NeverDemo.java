package com.rxjava3.creates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class NeverDemo {
    public void create(){
        Observable.never().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(Object s) {
                System.out.println("never->onNext");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("never->onError");
            }

            @Override
            public void onComplete() {
                System.out.println("never->onComplete");
            }
        });
    }
}
