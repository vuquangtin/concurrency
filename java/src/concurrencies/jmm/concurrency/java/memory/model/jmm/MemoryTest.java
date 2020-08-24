package concurrency.java.memory.model.jmm;

import java.util.concurrent.Exchanger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MemoryTest {

	public static void main(String[] args) {
		testThread();
	}

	public static void testRunnable() {
		MyRunnable runnable = new MyRunnable(100, "MyRunnable");
		Thread myThreadRunnable = new Thread(runnable);
		myThreadRunnable.start();
		int i = 0;
		while (true) {
			i++;
			System.out.println(runnable.getA() + "\t" + runnable.getB());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i > 10)
				break;
		}
	}

	public static void testThread() {
		Exchanger<String> mutex = new Exchanger<String>();
		MyThread myThread = new MyThread(100, "MyRunnable",mutex);
		myThread.start();
		int i = 0;
		while (true) {
			
			i++;
			System.out.println(myThread.getA() + "\t" + myThread.getB());
			try {
				Thread.sleep(1000);
				mutex.exchange(i+"");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (i > 10)
				break;
		}

	}

}
