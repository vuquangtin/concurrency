package concurrentcollections;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <h1>Java CopyOnWriteArrayList Examples</h1> Let’s see a couple of examples in
 * action. The first one creates two threads:
 * <ul>
 * <li>
 * Thread Writer adds a number to CopyOnWriteArrayList for every 5 seconds.</li>
 * <li>
 * Thread Reader iterates the list repeatedly with a small delay (10
 * milliseconds) for every iteration.</li>
 * </ul>
 * That means the read operations outnumber the write ones, and here’s the full
 * source code of the program:
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 * @see https
 *      ://www.codejava.net/java-core/concurrency/java-concurrent-collection
 *      -copyonwritearraylist-examples
 *
 */
public class CopyOnWriteArrayListExample {

	public static void main(String[] args) {

		List<Integer> list = new CopyOnWriteArrayList<>();

		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);

		new WriteThread("Writer", list).start();

		new ReadThread("Reader", list).start();

	}
}

class WriteThread extends Thread {

	private List<Integer> list;

	public WriteThread(String name, List<Integer> list) {
		this.list = list;
		super.setName(name);
	}

	public void run() {
		int count = 6;

		while (true) {

			try {

				Thread.sleep(5000);

			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			list.add(count++);

			System.out.println(super.getName() + " done");
		}
	}
}

class ReadThread extends Thread {
	private List<Integer> list;

	public ReadThread(String name, List<Integer> list) {
		this.list = list;
		super.setName(name);
	}

	public void run() {

		while (true) {

			String output = "\n" + super.getName() + ":";

			Iterator<Integer> iterator = list.iterator();

			while (iterator.hasNext()) {
				Integer next = iterator.next();
				output += " " + next;

				// fake processing time
				try {

					Thread.sleep(10);

				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}

			System.out.println(output);
		}
	}
}
