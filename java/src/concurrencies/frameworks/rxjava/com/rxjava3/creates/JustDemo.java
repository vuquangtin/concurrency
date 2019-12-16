package com.rxjava3.creates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * @author ousiyuan
 * @date 2019/9/30
 */
public class JustDemo {
    public static void main(String[] args) {
        Observable.just(1, 2, 3, 4).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer num) throws Exception {
                System.out.println(num+"*");
            }
        });
    }
}
