package com.rxjava3.errors;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.BooleanSupplier;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class RetryDemo {
    public static void main(String[] args) {
        Observable observable = Observable.create(observableEmitter -> {
            for (int i = 0; i < 5; i++){
                if (i == 2){
                    observableEmitter.onError(new Exception("异常测试"));
                }else{
                    observableEmitter.onNext(i+"");
                }
            }
            observableEmitter.onComplete();
        });

        // 不加参数就是无限地retry
//        observable.retry(2).subscribe(num -> System.out.println(num+"*")
//                , throwable -> System.out.println("异常测试"));

        observable.retryUntil(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() throws Throwable {
                // true就表示不retry了，进入异常
                // false就表示继续retry
                return true;
            }
        }).subscribe(num -> System.out.println(num+"*")
                , throwable -> System.out.println("异常测试"));
    }
}
