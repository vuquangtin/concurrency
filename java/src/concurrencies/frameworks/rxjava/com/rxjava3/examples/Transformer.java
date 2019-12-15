package com.rxjava3.examples;

public interface Transformer<T, R> {
	R call(T from);
}