package com.rxjava3.examples;

public interface Observer<T> {
    default void completed(){
        System.out.println("complete");
    };
    void onNext(T t);
}