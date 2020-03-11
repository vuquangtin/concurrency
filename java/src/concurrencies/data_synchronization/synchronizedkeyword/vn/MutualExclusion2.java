package synchronizedkeyword.vn;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MutualExclusion2 implements Runnable {

	static int count = 0;

	public void add() {
		for (int i = 0; i < 10; i++) {
			synchronized (this) {
				count++;
			}
		}
	}

	@Override
	public void run() {
		add();
	}

	public static void main(String[] args) throws InterruptedException {

		MutualExclusion2 x = new MutualExclusion2();
		for (int i = 0; i < 500; i++) {
			Thread y = new Thread(x);
			y.start();
		}
		Thread.sleep(1000);
		System.out.println(x.count);
	}

}
