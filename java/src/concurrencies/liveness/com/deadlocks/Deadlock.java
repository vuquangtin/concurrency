package com.deadlocks;

class A {
	synchronized void methodA(B b) {
		b.last();
	}

	synchronized void last() {
		System.out.println(" Inside A.last()");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class B {
	synchronized void methodB(A a) {
		a.last();
	}

	synchronized void last() {
		System.out.println(" Inside B.last()");
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class Deadlock implements Runnable {
	A a = new A();
	B b = new B();

	// Constructor
	Deadlock() {
		Thread t = new Thread(this);
		t.start();
		a.methodA(b);
	}

	public void run() {
		b.methodB(a);
	}

	public static void main(String args[]) {
		new Deadlock();
	}
}