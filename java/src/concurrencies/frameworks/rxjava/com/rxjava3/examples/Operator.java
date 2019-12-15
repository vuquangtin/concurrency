package com.rxjava3.examples;

public interface Operator<T, R> {
    public Subscriber<? super R> call(Subscriber<? super R> subscriber);
}