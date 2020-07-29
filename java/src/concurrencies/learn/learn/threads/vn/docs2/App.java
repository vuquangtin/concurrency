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
public class App {
	public static void main(String[] args) {
		Thread[] subThreads = new Thread[5];

		Thread mainThread = Thread.currentThread();
		System.out.println("Main thread's name:" + mainThread.getName());

		for (int i = 0; i < subThreads.length; i++) {
			subThreads[i] = new SimpleThread(i);
		}

		System.out.println("Start sub-threads:");
		for (int i = 0; i < subThreads.length; i++) {
			subThreads[i].start();
		}

		System.out.println("Main thread has been done!");
	}
}

class Counter {
	int count = 0;

	public void tang() {
		count = count + 1;
	}
}