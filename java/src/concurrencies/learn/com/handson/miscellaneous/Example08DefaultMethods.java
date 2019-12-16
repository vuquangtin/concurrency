/**
 * 
 */
package com.handson.miscellaneous;

import static java.lang.System.out;

/**
 * @author sveera
 *
 */
public class Example08DefaultMethods {

	/*
	 * Example on Default Methods.Default methods are introduced to add new methods
	 * to existing interface by without breaking existing code.Default methods was
	 * introduced to add method in Collection interface to support streams.
	 */
	public static void main(String[] args) {
		TestInterface testInterface1 = new TestClassA();
		TestInterface testInterface2 = new TestClassB();
		TestInterface testInterface3 = new TestClassC();
		out.println(testInterface1.getInfo());
		out.println(testInterface2.getInfo());
		out.println(testInterface3.getInfo());
	}

	private static interface TestInterface {

		default String getInfo() {
			return "This is default method from " + TestInterface.class.getSimpleName();
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
