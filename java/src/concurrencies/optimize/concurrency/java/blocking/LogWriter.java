package concurrency.java.blocking;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class LogWriter {
	private final BlockingQueue<String> queue;
	private final LoggerThread loggerThread;

	public LogWriter(PrintWriter printWriter) {
		this.queue = new LinkedBlockingQueue<String>();
		this.loggerThread = new LoggerThread(printWriter);
	}

	public void start() {
		loggerThread.start();
	}

	public void log(String msg) throws InterruptedException {
		queue.put(msg);
	}

	private class LoggerThread extends Thread {
		private final PrintWriter writer;

		private LoggerThread(PrintWriter writer) {
			this.writer = writer;
		}

		@Override
		public void run() {

			try {
				while (true) {
					writer.println(queue.take());
				}
			} catch (InterruptedException e) {
				writer.close();
				// e.printStackTrace();
			}
		}
	}
}