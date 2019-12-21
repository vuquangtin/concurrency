package com.books.nested;

import com.books.nested.Outer.Inner;

public class OuterTest {
	public static void main(String[] args) {
		Outer outer = new Outer();
		Outer.Inner inner = outer.new Inner();
		inner.getCounter();
		Outer.Inner inner2 = new Outer().new Inner();
	}
}
