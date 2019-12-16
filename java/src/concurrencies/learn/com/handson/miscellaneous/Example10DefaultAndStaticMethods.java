/**
 * 
 */
package com.handson.miscellaneous;

import static java.lang.System.out;

/**
 * @author sveera
 *
 */
public class Example10DefaultAndStaticMethods {

	/* Example on Interface with both default and static methods */
	public static void main(String[] args) {
		TestInterface testInterface1 = new TestClassA();
		TestInterface testInterface2 = new TestClassB();
		TestInterface testInterface3 = new TestClassC();
		out.println(testInterface1.getInfo());
		out.println(testInterface2.getInfo());
		out.println(testInterface3.getInfo());
		out.println(TestInterface.greetUser("Veera"));
	}

	private static interface TestInterface {

		default String getInfo() {
			return "This is default method from " + TestInterface.class.getSimpleName();
		}

		static String greetUser(String user) {
			return prepareGreetMsg(user);
		}

		//private 
		static String prepareGreetMsg(String user) {
			return String.format("Hello %s , Greeting's from God !!!", user);
		}
	}

	private static class TestClassA implements TestInterface {

		@Override
		public String getInfo() {
			return "This is TestClassA method from " + this.getClass().getSimpleName() + " class";
		}

	}

	private static class TestClassB implements TestInterface {

	}

	private static class TestClassC extends TestClassA implements TestInterface {

	}

}
