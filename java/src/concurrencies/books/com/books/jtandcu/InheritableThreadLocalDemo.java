package com.books.jtandcu;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class InheritableThreadLocalDemo {
	private static final InheritableThreadLocal<Integer> intVal = new InheritableThreadLocal<Integer>();

	public static void main(String[] args) {
		Runnable rP = () -> {
			intVal.set(new Integer(10));
			Runnable rC = () -> {
				Thread thd = Thread.currentThread();
				String name = thd.getName();
				System.out.printf("%s %d%n", name, intVal.get());
			};
			Thread thdChild = new Thread(rC);
			thdChild.setName("Child");
			thdChild.start();
		};
		new Thread(rP).start();
	}
}