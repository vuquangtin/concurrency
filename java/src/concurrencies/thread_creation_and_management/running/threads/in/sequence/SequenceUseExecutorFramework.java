package running.threads.in.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 * In the first case the join for each thread causes the threads to wait for one
 * another. In the second case a list stores the threads and executor executes
 * them one after another creating 3 threads
 * 
 * Another way to do this is where only one runnable class is present and
 * communication between thread is done via static variable in the main class
 * and a variable in the runnable class
 * 
 * @author admin
 *
 */
public class SequenceUseExecutorFramework {
	int valve = 1;

	public static void main(String[] args) {
		SequenceUseExecutorFramework s = new SequenceUseExecutorFramework();
		ExecutorService es = Executors.newFixedThreadPool(3);

		List<Runnable> rList = new ArrayList<>();
		rList.add(new ClassA(s));
		rList.add(new ClassB(s));
		rList.add(new ClassC(s));

		for (int i = 0; i < rList.size(); i++) {
			es.submit(rList.get(i));
		}
		es.shutdown();

	}
}

class ClassA implements Runnable {
	SequenceUseExecutorFramework s;

	ClassA(SequenceUseExecutorFramework s) {
		this.s = s;
	}

	public void run() {
		synchronized (s) {
			for (int i = 0; i < 10; i++) {
				while (s.valve != 1) {
					try {
						s.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("A");
				s.valve = 2;
				s.notifyAll();
			}
		}
	}
}

class ClassB implements Runnable {
	SequenceUseExecutorFramework s;

	ClassB(SequenceUseExecutorFramework s) {
		this.s = s;
	}

	public void run() {
		synchronized (s) {
			for (int i = 0; i < 10; i++) {
				while (s.valve != 2) {
					try {
						s.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("B");
				s.valve = 3;
				s.notifyAll();
			}
		}
	}
}

class ClassC implements Runnable {
	SequenceUseExecutorFramework s;

	ClassC(SequenceUseExecutorFramework s) {
		this.s = s;
	}

	public void run() {
		synchronized (s) {
			for (int i = 0; i < 10; i++) {
				while (s.valve != 3) {
					try {
						s.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("C");
				s.valve = 1;
				s.notifyAll();
			}
		}
	}
}