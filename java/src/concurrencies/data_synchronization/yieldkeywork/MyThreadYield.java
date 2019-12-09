package yieldkeywork;

import java.util.ArrayList;
import java.util.List;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class MyThreadYield {
	public static void main(String a[]) {
		List<ExmpThread> list = new ArrayList<ExmpThread>();
		for (int i = 0; i < 3; i++) {
			ExmpThread et = new ExmpThread(i + 5);
			list.add(et);
			et.start();
		}

		for (ExmpThread et : list) {
			try {
				et.join();
			} catch (InterruptedException ex) {
			}
		}
	}
}

class ExmpThread extends Thread {

	private int stopCount;

	public ExmpThread(int count) {
		this.stopCount = count;
	}

	public void run() {
		for (int i = 0; i < 50; i++) {
			if (i % stopCount == 0) {
				System.out.println("Stoping thread: " + getName());
				yield();
			}
		}
	}
}