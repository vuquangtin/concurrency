package concurrency.java.blocking;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.concurrent.GuardedBy;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class LogService {
	private final BlockingQueue<String> queue;
	private final LoggerThread loggerThread;
	private final PrintWriter writer;
	@GuardedBy("this")
	private boolean isShutdown;
	@GuardedBy("this")
	private int reservations;

	public LogService(PrintWriter printWriter) {
		this.queue = new LinkedBlockingQueue<>();
		this.loggerThread = new LoggerThread();
		this.writer = printWriter;
		this.isShutdown = false;
	}

	public void start() {
		loggerThread.start();
	}

	public void stop() {
		synchronized (this) {
			isShutdown = true;
		}
		loggerThread.interrupt();
	}

	public void log(String msg) throws InterruptedException {
		synchronized (this) {
			if (isShutdown) {
				throw new IllegalStateException("日志服务已停止");
			}
			++reservations;
		}
		queue.put(msg);
	}

	private class LoggerThread extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					try {
						synchronized (LogService.this) {
							if (isShutdown && reservations == 0) {
								break;
							}
						}
						String msg = queue.take();
						synchronized (LogService.this) {
							--reservations;
						}
						writer.println(msg);
					} catch (InterruptedException e) {
						// e.printStackTrace();
						/* 重试 */
					}
				}
			} finally {
				writer.close();
			}

		}
	}

}