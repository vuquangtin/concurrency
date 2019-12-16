/**
 * 
 */
package com.handson.generics;

import java.util.List;

/**
 * @author veera
 *
 */
public class GenericsTest8Super {

	public static void main(String args[]) {

		List<Integer> ints = null;
		List<Number> numbers = null;
		List<Object> objs = null;
		consumeList(ints);
		consumeList(numbers);
		consumeList(objs);

	}

	/**
	 * Using lower bound (? super Integer) can only consume value of type specified
	 * in lower bound (Integer in this example). But it cannot be used to produce
	 * value i.e You cann't do get operation on list to any type apart from Object
	 * type.This is due to lower bound list can hold any list of type's
	 * (List<Integr,List<Number>,List<Object>), So compiler is not sure to identify
	 * the type apart from Object type (Which is super class to all types).
	 * 
	 */
	private static void consumeList(List<? super Integer> superList) {

		superList.add(Integer.valueOf(1));
	}

}
