package basic.jmm;

import java.util.Random;

class MyRunnable implements Runnable {

	public void run() {
		methodOne();
	}

	public void methodOne() {
		int localVariable1 = 45;

		MySharedObject localVariable2 = MySharedObject.sharedInstance;

		// ... do more with local variables.
		System.out.println(localVariable2.member1);
		System.out.println(localVariable1);
		localVariable1 += new Random().nextInt();
		System.out.println("Random:" + localVariable1);
		methodTwo();
	}

	public void methodTwo() {
		Integer localVariable1 = new Integer(99);
		System.out.println(localVariable1);
		// ... do more with local variable.
	}
}

class MySharedObject {

	// static variable pointing to instance of MySharedObject

	public static final MySharedObject sharedInstance = new MySharedObject();

	// member variables pointing to two objects on the heap

	public Integer object2 = new Integer(22);
	public Integer object4 = new Integer(44);

	public long member1 = 12345;
	public long member2 = 67890;
}

public class JavaMemoryModel {
	public static void main(String[] args) {

		Thread t1 = new Thread(new MyRunnable());
		t1.start();
		MyRunnable ob = new MyRunnable();
		Thread t2 = new Thread(ob);
		t2.start();
	}
}