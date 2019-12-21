package com.books.nested;

public class NewEnclosing {

	void run() {
		 int k=100;
		class Local {
			public Local() {

			}

			void run() {
				// method implementation
				test();
				System.out.println(k);
				//k=100;
			}
		}
		Local local = new Local();
		local.run();
	}

	public void test() {
		NewEnclosing newEnclosing = new NewEnclosing();
		newEnclosing.run();
	}
}