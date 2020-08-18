package concurrency.java.optimize.tasks;

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
public class MyRunnable implements Runnable {
	private int id;
	private String name;

	public MyRunnable(String name) {
		this.name = name;
	}

	public MyRunnable() {
	}

	public MyRunnable(int id) {
		this.id = id;
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

	}

	public String getName() {
		
		return this.name;
	}

}
