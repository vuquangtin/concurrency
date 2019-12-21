package executors.executor;

import java.util.Date;

public class MyThread extends Thread {
	private int id;

	public MyThread(int id) {
		this.id = id;
	}

	public void run() {
		System.out.printf("Time %tr: " + " Thread " + id + " go to sleep\n",
				new Date());
		try {
			this.sleep();
		} catch (Exception e) {
		} finally {
			System.out.printf("Time %tr: " + " Thread " + id + " sleep done \n",
					new Date());
		}
	}

	private void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}