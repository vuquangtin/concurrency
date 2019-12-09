package blockingqueues.arrays;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ArrayBlockingQueueTest {
	public static void main(String[] args) {

		final int FILE_QUEUE_SIZE = 10;
		final int SEARCH_THREAD_SIZE = 100;

		BlockingQueue<File> queue = new ArrayBlockingQueue<File>(
				FILE_QUEUE_SIZE);

		Scanner in = new Scanner(System.in);
		System.out.print("Enter base directory: ");
		String directory = in.nextLine();
		System.out.print("Enter keyword (e.g. volatile): ");
		String keyword = in.next();

		FileEnumeratorTask enumerator = new FileEnumeratorTask(queue, new File(
				directory));
		new Thread(enumerator).start();

		for (int i = 0; i < SEARCH_THREAD_SIZE; ++i) {
			new Thread(new SearchTask(queue, keyword)).start();
		}
		in.close();
	}
}
