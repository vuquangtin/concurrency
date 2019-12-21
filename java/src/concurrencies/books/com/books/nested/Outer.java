package com.books.nested;

public class Outer {
	public void callInner() {
		int m = new Inner().counter;
	}

	private void callOutter() {
		int m = new Inner().counter;
	}

	public class Inner {
		// ...
		private int counter;
		public int TOTAL;

		// public static int STATIC_TOTAL;
		public static final int STATIC_TOTAL = 100;

		public int getCounter() {
			return counter;
		}

		// public static int getCounter2() {
		// return 1;
		// }
		public void setCounter(int counter) {
			this.counter = counter;
			callInner();
			callOutter();
		}
	}
}