package learn.threads.jakob_jenkov;

import concurrencies.base.TemplateMethod;

/**
 * https://www.youtube.com/playlist?list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class JMM extends TemplateMethod {

	public static void main(String[] args) {
		new JMM().runTemplateMethod(args);

	}

	private static class MyRunnable implements Runnable {
		MyObject o;

		MyRunnable(MyObject o) {
			this.o = o;
		}

		public void run() {
			o.foo();
		}
	}

	private static class MyObject {
		int counter = 0;

		synchronized void foo() {
			try {
				while (counter < 6) {
					Thread.sleep(500);
					counter++;
				}
			} catch (InterruptedException ignore) {
				// Ignore
			}
		}
	}
/****
 * @see https://github.com/afide/cunossensa/blob/e154e6ad6739614ea9ab4cb819c7708015b3dd01/dsa/src/main/java/MyThread.java
 */
	@Override
	public void implementionOne(String[] args) throws Exception {
		// Same reference to obj; only one will be allowed to call foo and the
		// other will be forced to wait
		MyObject obj = new MyObject();
		MyRunnable instance1 = new MyRunnable(obj);
		MyRunnable instance2 = new MyRunnable(obj);
		Thread thread1 = new Thread(instance1);
		Thread thread2 = new Thread(instance2);
		thread1.start();
		thread2.start();
		// Waits until above thread counts to 6 (slowly)
		while (instance1.o.counter != 5) {
			System.out.print(instance1.o.counter);
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while (instance2.o.counter != 5) {
			System.out.print(instance2.o.counter);
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void implementionTwo(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionThree(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionFour(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionFive(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionSix(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionSeven(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionEight(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionNine(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionTen(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

}
