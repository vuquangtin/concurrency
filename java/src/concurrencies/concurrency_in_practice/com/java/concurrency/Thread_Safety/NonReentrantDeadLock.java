package com.java.concurrency.Thread_Safety;

/**
 * NonreentrantDeadlock
 * <p/>
 * Code that would deadlock if intrinsic locks were not reentrant
 *
 * @author Brian Goetz and Tim Peierls
 */

class Widget {
	public synchronized void doSomething() {
	}
}

public class NonReentrantDeadLock extends Widget {
	public synchronized void doSomething() {
		System.out.println(toString() + ": calling doSomething");
		super.doSomething();
	}
}