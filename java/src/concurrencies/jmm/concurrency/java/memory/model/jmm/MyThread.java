package concurrency.java.memory.model.jmm;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyThread extends Thread {
	private int a;
	private String b;
	private Exchanger<String> mutex;

	public MyThread(int a, String b, Exchanger<String> mutex) {
		this.setA(a);
		this.setB(b);
		this.mutex = mutex;
	}

	@Override
	public void run() {
		int i = 0;
		while (true) {

			setA(i);
			b = b + i;
			i++;
			try {
				mutex.exchange(b);
				Thread.sleep(1000);

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (i > 10)
				break;

		}

	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}
}
