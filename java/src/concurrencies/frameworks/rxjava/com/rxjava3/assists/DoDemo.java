package com.rxjava3.assists;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author ousiyuan
 * @date 2019/11/5
 */
public class DoDemo {
    public static void main(String[] args) {
        // 每次onnext之前执行的一个操作
        Observable.just(1,2,3)
                .doOnNext(num ->System.out.println(num+"++"))
                .subscribe(num -> System.out.println(num+"*"));

        // doOnSubscribe doOnUnsubscribe doOnCompleted doOnError
        // doOnTerminate终止前调用，不过completed还是error
        // finallyDo终止后调用
    }
}
