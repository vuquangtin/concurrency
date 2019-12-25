package com.rxjava3.reactivex.rxjava;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class NonDaemonInterval {

    public static void main(String[] args) throws InterruptedException {
    	ExecutorService executor =Executors.newCachedThreadPool();
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnNext(System.out::println)
                .subscribeOn(Schedulers.from(executor)).subscribe();
        //Schedulers.from(executor);
        //Thread.sleep(5_000);
        
        ThreadPoolExecutor exec = new ThreadPoolExecutor(
                0, 64, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        exec.allowCoreThreadTimeOut(true);

        Scheduler s = Schedulers.from(exec);

        Observable
        .fromArray(Arrays.asList("one", "two", "three"))
        .doOnNext(word -> System.out.printf("%s uses thread %s%n", word,
            Thread.currentThread().getName()))
        .subscribeOn(s)
        .subscribe(word -> System.out.println(word));
    }
}


