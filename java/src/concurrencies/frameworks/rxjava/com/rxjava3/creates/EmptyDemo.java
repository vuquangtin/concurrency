package com.rxjava3.creates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author ousiyuan
 * @date 2019/9/30
 */
public class EmptyDemo {
    public void create(){
        Observable.empty().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(Object s) {
                System.out.println("empty->onNext");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("empty->onError");
            }

            @Override
            public void onComplete() {
                System.out.println("empty->onComplete");
            }
        });
    }
}
