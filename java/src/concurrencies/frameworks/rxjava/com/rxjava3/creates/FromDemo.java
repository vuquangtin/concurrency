package com.rxjava3.creates;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.Arrays;
import java.util.List;

/**
 * @author ousiyuan
 * @date 2019/9/30
 */
public class FromDemo {
    public static void main(String[] args) {
        Integer[] items = {1, 2, 3, 4};
        Observable.fromArray(items).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer num) throws Exception {
                System.out.println(num+"*");
            }
        });

        // 第二种写法
        Observable.fromArray(items).subscribe(num -> System.out.println(num+"*"));

        List<Integer> list = Arrays.asList(1,2,3,4);
        Observable.fromIterable(list).subscribe(num -> System.out.println(num+"*"));
        // future用法
//        Observable.fromFuture(new Future<String>() {
//        });
    }
}
