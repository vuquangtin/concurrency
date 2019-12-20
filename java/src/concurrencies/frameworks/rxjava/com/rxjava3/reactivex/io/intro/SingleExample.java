package com.rxjava3.reactivex.io.intro;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import com.rxjava3.change.entities.Order;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SingleExample {

    public void just() {
        Single<String> source = Single.just("Hello Single");
        source.subscribe(System.out::println);
    }


    public void fromObservable() {
        // 1
        Observable<String> source = Observable.just("Hello Single");
        Single.fromObservable(source)
                .subscribe(System.out::println);

        // 2
        Observable.just("Hello Single")
                .single("default item")
                .subscribe(System.out::println);

        // 3
        String[] colors = {"Red", "Blue", "Gold"};
        Observable.fromArray(colors)
                .first("default value")
                .subscribe(System.out::println);

        // 4
        Observable.empty()
                .single("default value")
                .subscribe(System.out::println);

        // 5
        Observable.just(new Order("ORD-1"), new Order("ORD-2"))
                .take(1)
                .single(new Order("default order"))
                .subscribe(System.out::println);
    }

    public void errorCase() {
        Single<String> source =
                Observable.just("Hello Single", "Error")
                        .single("default item");
        source.subscribe(System.out::println);
    }

    public static void main(String[] args) {
        SingleExample demo = new SingleExample();
        demo.just();
        demo.fromObservable();
        //demo.errorCase();
    }
}


