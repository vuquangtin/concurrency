package concurrency.java.optimize.tasks;

import java.util.Random;
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
public class MyRunnableSemaphore implements Runnable {
	private int id;
	private String name;
	Semaphore semaphone;

	public MyRunnableSemaphore(int id, Semaphore semaphone) {
		this.id = id;
		this.semaphone = semaphone;
	}

	@Override
	public void run() {
		System.out.println("start MyRunnable:" + id);
		try {
			Thread.sleep(1000 * (new Random().nextInt(10) + 1));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end MyRunnable:" + id);
		semaphone.release();

	}

	public String getName() {

		return this.name;
	}

}
