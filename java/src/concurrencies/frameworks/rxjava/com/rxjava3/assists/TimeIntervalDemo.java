package com.rxjava3.assists;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.Timed;

/**
 * @author ousiyuan
 * @date 2019/11/5
 * 得到发射数据的时间长度，第一个位订阅到发射的时间长度
 */
public class TimeIntervalDemo {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                for (int i = 0; i < 3; i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    observableEmitter.onNext(i+"");
                }
                observableEmitter.onComplete();
            }
        }).timeInterval(Schedulers.trampoline())
                .subscribe(new Consumer<Timed<String>>() {
                    @Override
                    public void accept(Timed<String> stringTimed) throws Throwable {
                        System.out.println(stringTimed.value() +"::" +stringTimed.time());
                    }
                });
    }
}
