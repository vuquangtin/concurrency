package com.books.nested;


public class OuterTest {
	public static void main(String[] args) {
		Outer outer = new Outer();
		Outer.Inner inner = outer.new Inner();
		inner.getCounter();
		Outer.Inner inner2 = new Outer().new Inner();
	}
}
