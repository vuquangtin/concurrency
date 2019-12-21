package com.books.nested;

public class Test {
	public static void main(String[] args) {
		new Enclosing().test();
		Enclosing.StaticNested nested = new Enclosing.StaticNested();
		//khong goi dc
		//nested.run();
		//new Enclosing().run();
		try{
			int y=0;
			int t=y/0;
			
		}finally {
			System.out.println("000");
		};
		System.out.println("ok 000");
	}
}
