package com.rxjava3.examples;
/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public interface Observer<T> {
    default void completed(){
        System.out.println("complete");
    };
    void onNext(T t);
}