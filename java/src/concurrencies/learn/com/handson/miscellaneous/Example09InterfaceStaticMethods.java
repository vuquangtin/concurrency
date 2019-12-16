/**
 * 
 */
package com.handson.miscellaneous;

import static java.lang.System.out;

/**
 * @author sveera
 *
 */
public class Example09InterfaceStaticMethods {

	/*
	 * Interface with static method. Interfaces can also have private static
	 * method.
	 */
	public static void main(String[] args) {
		out.println(TestInterface.greetUser("Veera"));
	}

	private static interface TestInterface {

		static String greetUser(String user) {
			return prepareGreetMsg(user);
		}

		//private 
		static String prepareGreetMsg(String user) {
			return String.format("Hello %s , Greeting's from God !!!", user);
		}
	}
}
