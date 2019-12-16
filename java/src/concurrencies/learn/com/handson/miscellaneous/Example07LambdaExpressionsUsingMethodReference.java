/**
 * 
 */
package com.handson.miscellaneous;

import static java.lang.System.out;

import java.util.stream.Stream;

/**
 * @author sveera
 *
 */
public class Example07LambdaExpressionsUsingMethodReference {

	/*
	 * Simplified notation of Lambda expression is Method Reference.Method reference
	 * comes in three variations they are method reference using static method,
	 * using instance method of local variable and using instance method on lambda
	 * expression arguments.
	 */
	public static void main(String[] args) {

		Stream<Integer> numbersSet1 = Stream.of(1, 2, 3, 4, 5);
		// In the below example method reference is created using static method(using
		// System.out.println).
		numbersSet1.forEach(out::println);
		out.println();
		Stream<String> stringTockens = Stream.of("Hello", " how", " are", " you ?");
		// In the below example method reference is created using instance method
		// on lambda expression arguments (String.concat() method).
		out.println(stringTockens.reduce("", String::concat));

	}

}
