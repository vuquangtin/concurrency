package com.rxjava3.creates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author ousiyuan
 * @date 2019/9/30
 */
public class ThrowDemo {
    public void create(){
        Observable.error(new Exception("测试异常")).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(Object s) {
                System.out.println("error->onNext");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("error->onError: "+throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("error->onComplete");
            }
        });
    }
}
