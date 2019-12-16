package com.rxjava3.merges;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author ousiyuan
 * @date 2019/10/24
 */
public class SwitchDemo {
    public static void main(String[] args) {
        Observable observable1 = Observable.just(1,2,3);
        Observable observable2 = Observable.just(4,5,6);
//        observable1.switchIfEmpty(observable2).subscribe(num -> System.out.println(num+""));

        // 如果observable为空，就会选择observable2
        Observable.empty().switchIfEmpty(observable2).subscribe(num -> System.out.println(num+""));
    }
}
