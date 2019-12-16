package com.rxjava3.assists;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;

import java.util.Map;

/**
 * @author ousiyuan
 * @date 2019/11/5
 */
public class ToDemo {
    public static void main(String[] args) {
//        Observable.just(1, 2, 3, 4).toList().subscribe(new Consumer<List<Integer>>() {
//            @Override
//            public void accept(List<Integer> integers) throws Throwable {
//                System.out.println(integers.get(3)+"++");
//            }
//        });

        Observable.just(1, 2, 3, 4).toMap(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Throwable {
                return integer+"";
            }
        }).subscribe(new Consumer<Map<String, Integer>>() {
            @Override
            public void accept(Map<String, Integer> map) throws Throwable {
                System.out.println(map.get("2")+"*");
            }
        });
    }
}
