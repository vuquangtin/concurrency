package com.rxjava3.creates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * @author ousiyuan
 * @date 2019/9/30
 */
public class RangeDemo {
    public static void main(String[] args) {
        Observable.range(2, 4).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer num) throws Exception {
                System.out.println(num+"*");
            }
        });
    }
}
