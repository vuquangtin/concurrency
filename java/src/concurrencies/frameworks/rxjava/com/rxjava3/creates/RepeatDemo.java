package com.rxjava3.creates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * @author ousiyuan
 * @date 2019/9/30
 */
public class RepeatDemo {
    public static void main(String[] args) {
        //        Observable.just(1, 2, 3, 4).repeat(3).subscribe(new Consumer<Integer>() {
        //            @Override
        //            public void accept(Integer num) throws Exception {
        //                System.out.println(num+"*");
        //            }
        //        });


        Observable.just(1, 2, 3, 4).repeatWhen(new Function<Observable<Object>, ObservableSource<
                ?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Throwable {
                //制定轮询时间，每一秒钟轮询一次
                //当发送error或者empty时间，轮询被终止
                // Observable.empty();
                //  Observable.error(new NullPointerException());
                return objectObservable.delay(1,TimeUnit.SECONDS, Schedulers.trampoline());
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(Integer s) {
                System.out.println(s + "*");
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
