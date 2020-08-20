package synchronizers.cyclicbarrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CyclicBarrierTest {
	final CyclicBarrier barrier;
	List<Integer> list = new ArrayList<Integer>();

	CyclicBarrierTest() {
		barrier = new CyclicBarrier(3, new Runnable() {
			public void run() {
				addListvalue();
				list = Collections.synchronizedList(new ArrayList<Integer>());
			}
		});
		list = Collections.synchronizedList(list);
		new Thread(new Task(1, 3)).start();
		new Thread(new Task(4, 6)).start();
		new Thread(new Task(7, 9)).start();
	}

	void add(int start, int end) {
		int sum = 0;
		for (int s = start; s <= end; s++) {
			sum += s;
		}
		list.add(sum);
		System.out.println("Per Thread Addition:" + sum);
	}

	void addListvalue() {
		int total = 0;
		for (int j = 0; j < list.size(); j++) {
			total += list.get(j);
		}
		System.out.println("Total addtion:" + total);
	}

	class Task implements Runnable {
		int start = 0;
		int end = 0;

		Task(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				add(start, end);
				try {
					barrier.await();
				} catch (InterruptedException ex) {
					return;
				} catch (BrokenBarrierException ex) {
					return;
				}
			}

		}
	}

	public static void main(String... args) {
		new CyclicBarrierTest();
	}
}
