package com.books.nested;

public class Enclosing {

	private static int x = 1;
	private int y = 1;

	private static void runStatic() {
	}

	public static class StaticNested {

		private void run() {
			// method implementation
			System.out.println("StaticNested" + x);
			// System.out.println("StaticNested"+y);
			// neu muoi truy cap vao y thi ta phai tao doi tuong cua lop outer;
			System.out.println("StaticNested" + new Enclosing().y);
			
			runStatic();
		}
	}

	private void run() {
		// method implementation
		System.out.println("Enclosing");
	}

	public void test() {
		Enclosing.StaticNested nested = new Enclosing.StaticNested();
		nested.run();
		new Enclosing().run();
	}
}