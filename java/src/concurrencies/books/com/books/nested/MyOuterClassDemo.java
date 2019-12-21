package com.books.nested;

public class MyOuterClassDemo {
	private int x = 1;

	public void doThings() {
		String name = "local variable";
		// inner class defined inside a method of outer class
		class MyInnerClassDemo {
			private int x = 5;

			public void seeOuter() {
				System.out.println("Outer Value of x is :" + x);
				System.out.println("Value of name is :" + name);// compilation
																// error!!
			} // close inner class method
			
		} // close inner class definition
		
		MyInnerClassDemo a=new MyInnerClassDemo();
		a.seeOuter();
	} // close Top level class method

	public static void main(String[] args) {
		new MyOuterClassDemo().doThings();
	}
} // close Top level class