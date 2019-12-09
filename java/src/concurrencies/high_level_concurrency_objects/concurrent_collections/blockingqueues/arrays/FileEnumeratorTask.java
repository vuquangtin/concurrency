package blockingqueues.arrays;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class FileEnumeratorTask implements Runnable {

	private BlockingQueue<File> queue;
	private File directory;

	public static File DUMMY = new File("");

	public FileEnumeratorTask(BlockingQueue<File> aQueue, File aDirectory) {
		this.queue = aQueue;
		this.directory = aDirectory;
	}

	public void run() {
		try {
			enumerate(directory);
			queue.put(DUMMY);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Recursively enumerates all files in a given directory and its
	 * subdirectories
	 * 
	 * @param aDirectory
	 *            the directory in which to start
	 */
	public void enumerate(File aDirectory) throws InterruptedException {
		File[] files = aDirectory.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				enumerate(file);
			else
				queue.put(file);
		}
	}
}