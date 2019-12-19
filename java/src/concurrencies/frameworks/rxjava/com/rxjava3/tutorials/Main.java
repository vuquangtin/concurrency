package com.rxjava3.tutorials;

import java.util.Arrays;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;
public class Main {

	public static void main(String... args) {
		Observable.fromArray(new Lambdas(), new MethodReference(), new Basics(), new MultiThreading(),
				new Intermediate(), new Advanced()).subscribe(Action::run);

		System.out.print(Arrays.asList(1, 2, 3, 4, 5, 6));
	}

}