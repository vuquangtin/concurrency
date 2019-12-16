package com.rxjava3.filters;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * @author ousiyuan
 * @date 2019/10/9
 */
public class ElementAtDemo {
    public static void main(String[] args) {
        Observable.just(1, 2, 3, 4, 5, 6)
                .elementAt(8)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {
                        System.out.println(s + "*");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        System.out.println(throwable.getMessage() + "+");
                    }
                });

        Observable.just(1, 2, 3, 4, 5, 6)
                .elementAtOrError(8)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {
                        System.out.println(s + "*");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        System.out.println(throwable.getMessage() + "*");
                    }
                });

        Observable.just(1, 2, 3, 4, 5, 6)
                .elementAt(8,0)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {
                        System.out.println(s + "*");
                    }
                });
    }
}
