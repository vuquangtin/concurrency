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
public class UsingJoinMethodInJava extends Thread {

	public UsingJoinMethodInJava(String name) {
		super(name);
	}

	@Override
	public void run() {
		System.out.println(getName());
		for (int i = 1; i <= 5; i++) {
			try {
				System.out.print(i + " ");
				Thread.sleep(300);
			} catch (InterruptedException ie) {
				System.out.println(ie.toString());
			}
		}
		System.out.println();
	}

	public static void main(String[] args) throws InterruptedException {
		UsingJoinMethodInJava t1 = new UsingJoinMethodInJava("Thread 1");
		UsingJoinMethodInJava t2 = new UsingJoinMethodInJava("Thread 2");
		t1.start();
		t1.join();
		t2.start();
		System.out.println("Main Thread Finished");
	}
}
