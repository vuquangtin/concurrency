package com.books.nested;

abstract class SimpleAbstractClass {
	abstract void run();
}

/**
 * @see https://www.baeldung.com/java-nested-classes
 * @author admin
 *
 */
public class AnonymousInnerTest {
	private int k = 0;

	public void test() {
		SimpleAbstractClass simpleAbstractClass = new SimpleAbstractClass() {
			private int m;

			void run() {
				// method implementation
				System.out.println("hhj:"+k);
				k=10;
				System.out.println("hhj:"+k);
			}

			public int getM() {
				return m;
			}

			public void setM(int m) {
				this.m = m;
			}
		};
		
		simpleAbstractClass.run();

	}

	public static void main(String[] args) {
		SimpleAbstractClass simpleAbstractClass = new SimpleAbstractClass() {
			private int m;

			void run() {
				// method implementation
			}
		};
		simpleAbstractClass.run();
		new AnonymousInnerTest().test();
	}
}
