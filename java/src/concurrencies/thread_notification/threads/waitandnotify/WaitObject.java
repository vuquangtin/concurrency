package threads.waitandnotify;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class WaitObject implements Runnable {

	int sum;

	@Override
	public void run() {
		synchronized (this) {
			int i = 0;
			while (i < 10000) {
				sum += i;
				i++;
				if (i == 1000)
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				System.out.println(i);
			}
			// se chay lai khi synchronized co chua wait()
			System.out.println("notify");
			notify();
		}
	}

	public static void main(String[] args) {
		WaitObject wait = new WaitObject();
		Thread t = new Thread(wait);
		SleepWaitObject sleepWait = new SleepWaitObject(wait);
		Thread t2 = new Thread(sleepWait);
		t2.start();
		t.start();
	}

	public int getSum() {
	
		return sum;
	}
}
