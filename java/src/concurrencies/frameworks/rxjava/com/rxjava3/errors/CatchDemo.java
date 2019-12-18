package com.rxjava3.errors;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CatchDemo {
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

//        observable.onErrorReturnItem(-1).subscribe(num -> System.out.println(num+"*"));

        observable.onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable o) throws Throwable {
                // 这个跟onErrorReturnItem不同，就是可以根据异常来选择return哪个Observable
                return -1;
            }
        }).subscribe(num -> System.out.println(num+"*"));

//        observable.onErrorResumeWith(Observable.just(-1,-2,-3)).subscribe(num -> System.out.println(num+"*"));

//        observable.onErrorResumeNext(new Function<Throwable, ObservableSource>() {
//            @Override
//            public ObservableSource apply(Throwable throwable) throws Throwable {
//                // 这个跟onErrorResumeWith不同，就是可以根据异常来选择return哪个Observable
//                return Observable.just(-1,-2,-3);
//            }
//        }).subscribe(num -> System.out.println(num+"*"));
    }
}
