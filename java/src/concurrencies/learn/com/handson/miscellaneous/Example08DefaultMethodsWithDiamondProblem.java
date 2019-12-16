/**
 * 
 */
package com.handson.miscellaneous;

import static java.lang.System.out;

/**
 * @author sveera
 *
 */
public class Example08DefaultMethodsWithDiamondProblem {

	/*
	 * Diamond Problem - Extending multiple interfaces having same default
	 * methods.When diamond problem occurs , java complier enforces you to override
	 * the method in implementing class.However, this will not happen when you are
	 * extending from a class having same method and implementing interface,compiler
	 * will consider's method from class and ignores method from interface.This is
	 * already explained in previous example.
	 */
	public static void main(String[] args) {
		TestInterfaceA testInterface1 = new TestClass();
		TestInterfaceA testInterface2 = new TestClassA();
		TestInterfaceA testInterface3 = new TestClassB();
		out.println(testInterface1.getInfo());
		out.println(testInterface2.getInfo());
		out.println(testInterface3.getInfo());
	}

	private static interface TestInterfaceA {

		default String getInfo() {
			return "This is default method from " + TestInterfaceA.class.getSimpleName();
		}
	}

	private static interface TestInterfaceB {

		default String getInfo() {
			return "This is default method from " + TestInterfaceB.class.getSimpleName();
		}
	}

	private static class TestClass implements TestInterfaceA, TestInterfaceB {

		@Override
		public String getInfo() {
			return "This is TestClass method from " + this.getClass().getSimpleName() + " class";
		}

	}

	private static class TestClassA implements TestInterfaceA, TestInterfaceB {

		@Override
		public String getInfo() {
			return TestInterfaceA.super.getInfo();
		}

	}

	private static class TestClassB implements TestInterfaceA, TestInterfaceB {

		@Override
		public String getInfo() {
			return TestInterfaceB.super.getInfo();
		}

	}

}
