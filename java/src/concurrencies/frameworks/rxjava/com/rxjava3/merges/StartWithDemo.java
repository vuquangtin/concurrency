package com.rxjava3.merges;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author ousiyuan
 * @date 2019/10/24
 */
public class StartWithDemo {
    public static void main(String[] args) {
        Observable observable1 = Observable.just(1,2,3,4);
        observable1.startWithArray(-1,-2,-3).subscribe(num -> System.out.println(num+""));
//        observable1.startWithItem() 放一个item
//        observable1.startWithIterable() 放列表
//        observable1.startWith() 放observable
    }
}
