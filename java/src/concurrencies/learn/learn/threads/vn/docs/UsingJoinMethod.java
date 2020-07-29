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
public class UsingJoinMethod extends Thread {

	public UsingJoinMethod(String name) {
		super(name);
	}

	@Override
	public void run() {
		System.out.println(getName());
		for (int i = 1; i <= 5; i++) {
			try {
				System.out.print(i + " ");
				Thread.sleep(500);
			} catch (InterruptedException ie) {
				System.out.println(ie.toString());
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		UsingJoinMethod t1 = new UsingJoinMethod("Thread 1");
		UsingJoinMethod t2 = new UsingJoinMethod("Thread 2");
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException ie) {
			System.err.println(ie.toString());
		}
		t2.start();
	}
}