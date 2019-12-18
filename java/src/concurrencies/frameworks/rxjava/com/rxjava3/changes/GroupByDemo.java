package com.rxjava3.changes;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observables.GroupedObservable;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class GroupByDemo {
    public static void main(String[] args) {
        String[] strs = {"同调怪兽", "永续魔法", "速攻魔法", "超量怪兽", "仪式怪兽", "反击陷阱", "场地魔法", "永续陷阱"};
        Observable.fromArray(strs).groupBy(s -> {
            if (s.contains("怪兽")) {
                return "怪兽";
            }
            else if (s.contains("魔法")) {
                return "魔法";
            }
            else {
                return "陷阱";
            }
        }).subscribe(new Observer<GroupedObservable<String, String>>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(GroupedObservable<String, String> groupedObservable) {
                groupedObservable.subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(groupedObservable.getKey() + "组: " + s);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println(groupedObservable.getKey() + "组完成");
                    }
                });
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("全部完成");
            }
        });
    }

    //    new Function<String, String>() {
    //        @Override
    //        public String apply(String s) throws Throwable {
    //            if (s.contains("怪兽")){
    //                return "怪兽";
    //            }else if (s.contains("魔法")){
    //                return "魔法";
    //            }else{
    //                return "陷阱";
    //            }
    //        }
    //    })
}
