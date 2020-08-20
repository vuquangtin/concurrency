package synchronizers.exchanger;

import java.util.concurrent.Exchanger;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
class MyThread extends Thread {
	Exchanger<Integer> exchanger;
	Integer i;

	public MyThread(Exchanger<Integer> exchanger, Integer i) {
		this.exchanger = exchanger;
		this.i = i;
	}

	public void run() {
		try {
			Integer i1 = exchanger.exchange(i);
			System.out.println(i1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class MyExchanger {
	public static void main(String[] args) {
		Exchanger<Integer> exchanger = new Exchanger<>();
		MyThread thread = new MyThread(exchanger, 12);
		MyThread thread2 = new MyThread(exchanger, 10);

		thread.start();
		thread2.start();
	}
}