package running.threads.in.sequence;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/***
 * https://stackoverflow.com/questions/11544843/interview-how-to-ensure-that-a-thread-runs-after-another
 * 
 * @author admin
 *
 */
class Worker implements Runnable {

	BlockingQueue<Integer> q = new LinkedBlockingQueue<>();
	Worker next = null; // next worker in the chain

	public void setNext(Worker t) {
		this.next = t;
	}

	public void accept(int i) {
		q.add(i);
	}

	@Override
	public void run() {
		while (true) {
			int i;
			try {
				i = q.take(); // this blocks the queue to fill-up
				System.out.println(Thread.currentThread().getName() + i);
				if (next != null) {
					next.accept(i + 1); // Pass the next number to the next
										// worker
				}
				Thread.sleep(500); // Just sleep to notice the printing.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class PrintNumbersSequentially {
	public static void main(String[] as) {

		Worker w1 = new Worker();
		Worker w2 = new Worker();
		Worker w3 = new Worker();

		w1.setNext(w2);
		w2.setNext(w3);
		w3.setNext(w1);

		new Thread(w1, "Thread-1: ").start();
		new Thread(w2, "Thread-2: ").start();
		new Thread(w3, "Thread-3: ").start();

		// Till here all the threads have started, but no action takes place as
		// the queue is not filled for any worker. So Just filling up one
		// worker.
		w1.accept(100);
	}
}