package executors.customthreadpoolexecutor;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class Java7_0350_MyThread implements Runnable {
	private String myName;
	private int count;
	private final long timeSleep;

	Java7_0350_MyThread(String name, int newcount, long newtimeSleep) {
		this.myName = name;
		this.count = newcount;
		this.timeSleep = newtimeSleep;
	}

	@Override
	public void run() {
		int sum = 0;
		for (int i = 1; i <= this.count; i++) {
			sum = sum + i;
		}
		System.out.println(myName + " thread has sum = " + sum + " and is going to sleep for " + timeSleep);
		try {
			Thread.sleep(this.timeSleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
