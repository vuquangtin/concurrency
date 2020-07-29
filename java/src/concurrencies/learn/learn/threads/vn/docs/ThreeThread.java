package learn.threads.vn.docs;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreeThread extends Thread {

	public ThreeThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			System.out.println(i + " " + getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
				System.err.println(ie.toString());
			}
		}
		System.out.println(getName() + " da hoan thanh!");
	}

	public static void main(String[] args) {
		ThreeThread tt1 = new ThreeThread("Tp. Ho Chi Minh");
		ThreeThread tt2 = new ThreeThread("Tp. Can Tho");
		tt1.start();
		tt2.start();
	}
}