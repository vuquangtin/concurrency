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
public class MyThreadLocal2 {

	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			new Thread(() -> {
				int data = new Random().nextInt();
				Person myThreadLocal = Person.getInstance();
				System.out.println("线程:" + Thread.currentThread().getName() + "data  " + data);
				myThreadLocal.setName("mobin" + data);
				myThreadLocal.setAge(data);
				new A().get();
				new B().get();
			}).start();
		}
	}

	static class A {
		public void get() {
			Person AThreadLocal = Person.getInstance();
			System.out.println("A线程" + Thread.currentThread().getName() + "的name" + AThreadLocal.getName() + "    age"
					+ AThreadLocal.getAge());
		}
	}

	static class B {
		public void get() {
			Person BThreadLocal = Person.getInstance();
			System.out.println("B线程" + Thread.currentThread().getName() + "的name" + BThreadLocal.getName() + "    age"
					+ BThreadLocal.getAge());
		}
	}

	static class Person {
		private Person() {
		}// 禁止外部直接创建对象

		private static ThreadLocal<Person> mapThreadLocal = new ThreadLocal<>();

		private static Person getInstance() {
			Person myThreadLocal = mapThreadLocal.get();
			if (myThreadLocal == null) {
				myThreadLocal = new Person();
				mapThreadLocal.set(myThreadLocal);
			}
			return myThreadLocal;
		}

		private String name;
		private int age;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}
}