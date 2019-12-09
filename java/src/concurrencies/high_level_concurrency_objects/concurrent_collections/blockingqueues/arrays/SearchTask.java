package blockingqueues.arrays;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class SearchTask implements Runnable {

	private BlockingQueue<File> queue;
	private String keyword;
	private boolean done;

	public SearchTask(BlockingQueue<File> aQueue, String aKeyword) {
		this.queue = aQueue;
		this.keyword = aKeyword;
	}

	public void run() {
		try {
			done = false;
			while (!done) {
				File file = queue.take();
				if (file == FileEnumeratorTask.DUMMY) {
					queue.put(file);
					done = true;
				} else
					search(file);
			}
		} catch (InterruptedException | IOException ex) {
			ex.printStackTrace();
		}
	}

	public void search(File aFile) throws IOException {
		Scanner in = new Scanner(new FileInputStream(aFile));
		int lineNumber = 0;
		while (in.hasNext()) {
			++lineNumber;
			String line = in.nextLine();
			if (line.contains(keyword))
				System.out.printf("%s: %d: %s%n", aFile.getPath(), lineNumber, line);
		}
		in.close();
	}

}
