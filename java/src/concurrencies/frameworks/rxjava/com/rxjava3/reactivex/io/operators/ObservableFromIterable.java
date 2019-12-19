package com.rxjava3.reactivex.io.operators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.rxjava3.change.entities.Order;

import io.reactivex.rxjava3.core.Observable;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class ObservableFromIterable {

	public void listExample() {
		List<String> names = new ArrayList<>();
		names.add("Jerry");
		names.add("William");
		names.add("Bob");

		Observable<String> source = Observable.fromIterable(names);
		source.subscribe(System.out::println);
	}

	public void setExample() {
		Set<String> cities = new HashSet<>();
		cities.add("seoul");
		cities.add("London");
		cities.add("Paris");

		Observable<String> source = Observable.fromIterable(cities);
		source.subscribe(System.out::println);
	}

	public void blockingQueueExample() {
		BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(100);
		orderQueue.add(new Order("ORD-1"));
		orderQueue.add(new Order("ORD-2"));
		orderQueue.add(new Order("ORD-3"));

		Observable<Order> source = Observable.fromIterable(orderQueue);
		source.subscribe(order -> System.out.println(order.getId()));
	}

	public static void main(String[] args) {
		ObservableFromIterable demo = new ObservableFromIterable();
		demo.listExample();
		demo.setExample();
		demo.blockingQueueExample();
	}
}
