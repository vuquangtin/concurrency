package com.rxjava3.creates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * @author ousiyuan
 * @date 2019/9/30
 */
public class IntervalDemo {
    public static void main(String[] args) {
//        Observable.interval(3, TimeUnit.SECONDS, Schedulers.trampoline()).subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long s) throws Exception {
//                System.out.println(s+"*");
//            }
//        });

        Observable.intervalRange(1, 4, 0,1, TimeUnit.SECONDS, Schedulers.trampoline()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long s) throws Exception {
                System.out.println(s+"*");
            }
        });
    }
}
