package com.rxjava3.changes;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.List;

/**
 * @author ousiyuan
 * @date 2019/9/30
 */
public class BufferDemo {
    public static void main(String[] args) {
//        Observable.just(1,2,3,4,5,6,7,8,9).buffer(3).subscribe(new Consumer<List<Integer>>() {
//            @Override
//            public void accept(List<Integer> integers) throws Exception {
//                System.out.println("--size:" + integers.size());
//                for (int integer : integers) {
//                    System.out.println("--value:" + integer);
//                }
//            }
//        });

        Observable.just(1,2,3,4,5,6,7,8,9).buffer(3, 2).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                System.out.println("--size:" + integers.size());
                for (int integer : integers) {
                    System.out.println("--value:" + integer);
                }
            }
        });
    }
}
