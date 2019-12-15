package running.threads.in.sequence;

import java.io.IOException;

public class UsingJoinMain {
	public static void main(String[] args) throws IOException {
		Thread t1 = new Thread(new A(), "1");
		Thread t2 = new Thread(new A(), "2");
		Thread t3 = new Thread(new A(), "3");

		t1.start();
		try {
			t1.join();
		} catch (Exception e) {

		}
		t2.start();
		try {
			t2.join();
		} catch (Exception e) {

		}
		t3.start();
		try {
			t3.join();
		} catch (Exception e) {

		}

	}
}

class A implements Runnable {
	public void run() {
		System.out.println(Thread.currentThread().getName());
	}
}