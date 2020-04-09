package common;

import java.util.Random;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyThreadLocal {

	private static ThreadLocal threadLocal = new ThreadLocal();

	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			new Thread(() -> {
				int data = new Random().nextInt();	
				threadLocal.set(data);
				System.out.println(Thread.currentThread().getName() + "--data:  " + data);
				new A().get();
				new B().get();
			}).start();
		}
	}

	static class A {
		public void get() {
			int data = (int) threadLocal.get();
			System.out.println("A线程 :  " + Thread.currentThread().getName() + "   data:  " + data);
		}
	}

	static class B {
		public void get() {
			int data = (int) threadLocal.get();
			System.out.println("B线程 :  " + Thread.currentThread().getName() + "   data:  " + data);
		}
	}
}