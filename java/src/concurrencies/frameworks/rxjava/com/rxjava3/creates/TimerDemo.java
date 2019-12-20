package com.rxjava3.creates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
public class TimerDemo {
    public static void main(String[] args) {
        Observable.timer(3, TimeUnit.SECONDS, Schedulers.trampoline()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(Long s) {
                System.out.println(s+"*");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("异常");
            }

            @Override
            public void onComplete() {
                System.out.println("完成");
            }
        });
    }
}
