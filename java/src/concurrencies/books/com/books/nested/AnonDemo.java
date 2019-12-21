package com.books.nested;

import java.lang.reflect.InvocationTargetException;

class Anon {
};

/***
 * @see https://dzone.com/articles/how-java-10-changes-the-way-we-use-anonymous-inner
 * @see https://www.journaldev.com/996/java-inner-class
 * @author admin
 *
 */
public class AnonDemo {
	public static void main(String[] args) {
		int m=100;
		Anon anonInner = new Anon() {
			private int x;
			public String toString() {
				return "Overriden"+x;
			};

			public void doSomething() {
				System.out.println("Blah");
			};
		};
		System.out.println(anonInner.toString());

		// java 10 cho phep dieu nay
		// anonInner.doSomething(); // Won't compile!
		// new Anon() { public void doSomething() { System.out.println("Woah");
		// } }.hello();
		Anon anonInner2 = new Anon() {
			public void hello() {
				System.out.println("Woah! "+m);
			};
		};
		try {
			anonInner2.getClass().getMethod("hello").invoke(anonInner2);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
};