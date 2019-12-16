package com.rxjava3.assists;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * @author ousiyuan
 * @date 2019/10/25
 */
public class DelayDemo {
    public static void main(String[] args) {
        // 每个数据发送都延迟
        Observable.just(1,2,3).delay(2000, TimeUnit.MILLISECONDS, Schedulers.trampoline()).subscribe(num -> System.out.println(num+""));
    }
}
