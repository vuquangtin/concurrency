package com.rxjava3.connects;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ConnectDemo {
    public static void main(String[] args) {
        ConnectableObservable connectableObservable = Observable.just(1,2,3,4).publish();
        connectableObservable.subscribe(num -> System.out.println(num +"*"));
        connectableObservable.subscribe(num -> System.out.println(num +"+"));
        connectableObservable.connect();

        // publish将普通的observable转换成可连接的observable
        // 可连接的observable订阅的时候不会发射数据，在connect的时候会发射数据
    }
}
