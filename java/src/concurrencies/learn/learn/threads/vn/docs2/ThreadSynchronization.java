package learn.threads.vn.docs2;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadSynchronization {
	public static void main(String args[]) {
		MyThread thread1 = new MyThread("thread1: ");
		MyThread thread2 = new MyThread("thread2: ");
		thread1.start();
		thread2.start();
		boolean thread1IsAlive = true;
		boolean thread2IsAlive = true;
		do {
			if (thread1IsAlive && !thread1.isAlive()) {
				thread1IsAlive = false;
				System.out.println("Thread 1 is dead.");
			}
			if (thread2IsAlive && !thread2.isAlive()) {
				thread2IsAlive = false;
				System.out.println("Thread 2 is dead.");
			}
		} while (thread1IsAlive || thread2IsAlive);
	}
}

class MyThread21 extends Thread {
	static String message[] = { "Java", "is", "hot,", "aromatic,", "and",
			"invigorating." };

	public MyThread21(String id) {
		super(id);
	}

	public void run() {
		SynchronizedOutput.displayList(getName(), message);
	}

	void randomWait() {
		try {
			sleep((long) (3000 * Math.random()));
		} catch (InterruptedException x) {
			System.out.println("Interrupted!");
		}
	}
}

class SynchronizedOutput {
	public static synchronized void displayList(String name, String list[]) {
		for (int i = 0; i < list.length; ++i) {
			MyThread t = (MyThread) Thread.currentThread();
			t.randomWait();
			System.out.println(name + list[i]);
		}
	}
}

class SpeechSynthesizer {
	synchronized void say(String words) {
		// Speak
	}
}

class SpreadSheet {

	int cellA1, cellA2, cellA3;

	synchronized int sumRow() {
		return cellA1 + cellA2 + cellA3;
	}

	synchronized void setRow(int a1, int a2, int a3) {
		cellA1 = a1;
		cellA2 = a2;
		cellA3 = a3;
	}

	// ...
	void myMethod() {
		synchronized (this) {
			// ...
		}
	}
}

class MyThing {

	synchronized void waiterMethod() {
		// Do some stuff

		// Now we need to wait for notifier to do something
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Continue where we left off
	}

	synchronized void notifierMethod() {
		// Do some stuff

		// Notify waiter that we've done it
		notify();

		// Do more things
	}

	synchronized void relatedMethod() {
		// Do some related stuff
	}
}