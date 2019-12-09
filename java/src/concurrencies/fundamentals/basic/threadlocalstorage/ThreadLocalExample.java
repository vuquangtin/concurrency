package basic.threadlocalstorage;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class ThreadLocalExample {

	public static class SomethingToRun implements Runnable {

		private ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " "
					+ threadLocal.get());

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}

			threadLocal.set(System.nanoTime());
			System.out.println(Thread.currentThread().getName() + " "
					+ threadLocal.get());
		}
	}

	public static void main(String[] args) {
		SomethingToRun sharedRunnableInstance = new SomethingToRun();

		Thread thread1 = new Thread(sharedRunnableInstance);
		Thread thread2 = new Thread(sharedRunnableInstance);

		thread1.start();
		thread2.start();
	}

}