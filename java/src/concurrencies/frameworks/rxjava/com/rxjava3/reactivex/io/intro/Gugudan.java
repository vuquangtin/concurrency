package com.rxjava3.reactivex.io.intro;

import java.util.Scanner;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class Gugudan {

	public void plainJava() {
		Scanner in = new Scanner(System.in);
		System.out.println("Gugudan Input:");
		int dan = Integer.parseInt(in.nextLine());
		for (int row = 1; row <= 9; ++row) {
			System.out.println(dan + " * " + row + " = " + dan * row);
		}
	}

	public void reactiveV1() {
		Scanner in = new Scanner(System.in);
		System.out.println("Gugudan Input:");
		int dan = Integer.parseInt(in.nextLine());

		Observable<Integer> source = Observable.range(1, 9);
		source.subscribe(row -> System.out.println(dan + " * " + row + " = " + dan * row));
	}

	public void reactiveV2() {
		Scanner in = new Scanner(System.in);
		System.out.println("Gugudan Input:");
		int dan = Integer.parseInt(in.nextLine());

		Function<Integer, Observable<String>> gugudan = num -> Observable.range(1, 9)
				.map(row -> num + " * " + row + " = " + num * row);
		Observable<String> source = Observable.just(dan).flatMap(gugudan);
		source.subscribe(System.out::println);
	}

	public void reactiveV3() {
		Scanner in = new Scanner(System.in);
		System.out.println("Gugudan Input:");
		int dan = Integer.parseInt(in.nextLine());

		Observable<String> source = Observable.just(dan)
				.flatMap(num -> Observable.range(1, 9).map(row -> num + " * " + row + " = " + num * row));
		source.subscribe(System.out::println);
	}

	public void usingResultSelector() {
		Scanner in = new Scanner(System.in);
		System.out.println("Gugudan Input:");
		int dan = Integer.parseInt(in.nextLine());

		Observable<String> source = Observable.just(dan).flatMap(gugu -> Observable.range(1, 9),
				(gugu, i) -> gugu + " * " + i + " = " + gugu * i);
		source.subscribe(System.out::println);
	}

	public static void main(String[] args) {
		Gugudan demo = new Gugudan();
		demo.plainJava();
		demo.reactiveV1();
		demo.reactiveV2();
		demo.reactiveV3();
		demo.usingResultSelector();
	}
}
