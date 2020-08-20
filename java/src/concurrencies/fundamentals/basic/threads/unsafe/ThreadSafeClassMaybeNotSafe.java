package basic.threads.unsafe;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadSafeClassMaybeNotSafe {

	static class ThreadSafe {

		private List<Integer> list = Lists.newArrayList();

		synchronized Integer getLength() {

			return list.size();
		}

		synchronized void removeFirst() {
			list.remove(0);
		}

		synchronized void insertElement() {
			list.add(getLength() + 1);
		}
	}

	static class Runner implements Runnable {

		private ThreadSafe threadSafe;

		Runner(ThreadSafe threadSafe) {
			this.threadSafe = threadSafe;
		}

		public void run() {

			if (threadSafe.getLength() > 0) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				threadSafe.removeFirst();
			} else if (threadSafe.getLength() == 0) {
				threadSafe.insertElement();
			}

		}
	}

	public static void main(String[] args) {

		ThreadSafe threadSafe = new ThreadSafe();
		for (Integer i = 0; i < 100; i++) {
			Thread thread = new Thread(new Runner(threadSafe));
			thread.start();
		}
	}
}