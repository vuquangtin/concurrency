package com.rxjava3.merges;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author ousiyuan
 * @date 2019/10/24
 * 和StartWith相反，接在后面，不过只能放observable
 */
public class ConcatDemo {
    public static void main(String[] args) {
        Observable observable1 = Observable.just(1,2,3,4);
        Observable observable2 = Observable.just(-1,-2,-3,-4);

        observable1.concatWith(observable2).subscribe(num -> System.out.println(num+""));
    }
}
