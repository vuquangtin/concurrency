/**
 * 
 */
package com.handson.miscellaneous;

import static java.lang.System.out;

/**
 * @author sveera
 *
 */
public class Example07LambdaExpressions {

	/*
	 * Example on Lambda Expression. Lambda expression can be applied on functional
	 * interface (interface with only one abstract method but it can have any no.of
	 * static and default methods). Lambda expressions are alternative to anonymous
	 * inner classes applied on functional interface. In Java 8 java.util.function
	 * package has several functional interfaces.
	 */
	public static void main(String[] args) {
		Runnable runnable = () -> {
			for (int i = 1; i <= 100; i++)
				out.println(i);
		};
		new Thread(runnable).start();
	}

}
