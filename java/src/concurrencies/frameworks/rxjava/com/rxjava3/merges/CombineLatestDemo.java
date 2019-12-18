package com.rxjava3.merges;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.BiFunction;

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
public class CombineLatestDemo {
    public static void main(String[] args) {
        Observable observable1 = Observable.intervalRange(1, 4, 0, 1000, TimeUnit.MILLISECONDS);
        Observable observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                Thread.sleep(500);
                observableEmitter.onNext(7);
                Thread.sleep(2000);
                observableEmitter.onNext(8);
            }
        });

        Observable.combineLatest(observable1, observable2, new BiFunction<Long, Integer, String>() {
            @Override
            public String apply(Long num1, Integer num2) throws Throwable {
                return num1 +":"+ num2;
            }
        }).subscribe(s -> System.out.println(s));

        /**
         *    1s    1s   1s
         * 1————2————3————4
         *    7————————8
         *  observable1发送的首个数据不打印，然后observable2发送7,7就和observable1最近发送的1合并，打印1:7，
         *  接着observable1发送2，2结合observable2上一次发送的7，打印2:7
         *  。。。
         *  当observable2把8发送完后，就不在打印了
         */
    }
}
