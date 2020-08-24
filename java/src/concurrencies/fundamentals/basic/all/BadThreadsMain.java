package basic.all;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BadThreadsMain {

	public static void main(String... args) throws InterruptedException {

		System.out.println("BadThreads start ...");
		BadThreads bt = new BadThreads();
		bt.demo();

		System.out.println("BadThreads with join start ...");
		BadThreadsJoin btj = new BadThreadsJoin();
		btj.demo();

		System.out.println("BadThreads with sleep ...");
		BadThreadsSleep badThreadsSleep = new BadThreadsSleep();
		badThreadsSleep.demo();

		System.out.println("BadThreads with swap string ...");
		BadThreadsSwapStrings btsw = new BadThreadsSwapStrings();
		btsw.demo();

		System.out.println("BadThreads with synchronized ...");
		BadThreadsSync badThreadsSync = new BadThreadsSync();
		badThreadsSync.demo();

		System.out.println("BadThreads with Lock ...");
		BadThreadsLock badThreadsLock = new BadThreadsLock();
		badThreadsLock.demo();

	}

	// Оригинальный класс BadThreads
	private static class BadThreads {
		static String message;

		public static class CorrectorThread extends Thread {
			@Override
			public void run() {
				message = "Помиловать";
			}
		}

		public void demo() throws InterruptedException {

			for (int i = 0; i < 1000; i++) {
				CorrectorThread correctorThread = new CorrectorThread();

				message = "Казнить";
				Thread.sleep(10);
				correctorThread.start();

				if (message.equalsIgnoreCase("Казнить"))
					System.out.println(message);
			}
		}
	}

	private static class BadThreadsJoin {
		static String message;

		private static class CorrectorThread extends Thread {
			@Override
			public void run() {
				message = "Помиловать";
			}
		}

		public void demo() throws InterruptedException {

			for (int i = 0; i < 1000; i++) {
				CorrectorThread correctorThread = new CorrectorThread();

				message = "Казнить";
				// Thread.sleep(10);
				correctorThread.start();

				correctorThread.join();
				if (message.equalsIgnoreCase("Казнить"))
					System.out.println(message);
			}
		}
	}

	private static class BadThreadsSleep {
		static String message;

		private static class CorrectorThread extends Thread {
			@Override
			public void run() {
				message = "Помиловать";
			}
		}

		public void demo() throws InterruptedException {

			for (int i = 0; i < 1000; i++) {
				CorrectorThread correctorThread = new CorrectorThread();

				message = "Казнить";
				Thread.sleep(10);
				correctorThread.start();

				Thread.sleep(1);

				if (message.equalsIgnoreCase("Казнить"))
					System.out.println(message);
			}
		}
	}

	private static class BadThreadsSwapStrings {
		static String message;

		private static class CorrectorThread extends Thread {
			@Override
			public void run() {
				message = "Помиловать";
			}
		}

		public void demo() throws InterruptedException {

			for (int i = 0; i < 20; i++) {
				CorrectorThread correctorThread = new CorrectorThread();

				correctorThread.start();
				Thread.sleep(10);
				message = "Казнить";

				if (message.equalsIgnoreCase("Казнить"))
					System.out.println(message);
			}
		}
	}

	private static class BadThreadsSync {
		static String message = "";

		public synchronized void setMessage(String newMessage) {
			synchronized (message) {
				message = newMessage;
			}
		}

		public synchronized String getMessage() {
			synchronized (message) {
				return message;
			}
		}

		private static class CorrectorThread extends Thread {

			@Override
			public synchronized void run() {
				synchronized (message) {
					message = "Помиловать";
				}
			}
		}

		public void demo() throws InterruptedException {

			for (int i = 0; i < 1000; i++) {

				CorrectorThread correctorThread = new CorrectorThread();

				// message = "Казнить";
				setMessage("Казнить");
				Thread.sleep(10);
				correctorThread.start();

				if (getMessage().equalsIgnoreCase("Казнить"))
					System.out.println(getMessage());
			}
		}
	}

	private static class BadThreadsLock {
		static String message = "";

		final Lock lock = new ReentrantLock();

		public void setMessage(String newMessage) {
			lock.lock();
			try {
				message = newMessage;
			} finally {
				lock.unlock();
			}
		}

		public synchronized String getMessage() {
			lock.lock();
			try {
				return message;

			} finally {
				lock.unlock();
			}
		}

		private class CorrectorThread extends Thread {

			@Override
			public synchronized void run() {
				lock.lock();
				try {
					message = "Помиловать";
				} finally {
					lock.unlock();
				}
			}
		}

		public void demo() throws InterruptedException {

			for (int i = 0; i < 1000; i++) {

				CorrectorThread correctorThread = new CorrectorThread();

				// message = "Казнить";
				setMessage("Казнить");
				Thread.sleep(10);
				correctorThread.start();

				if (getMessage().equalsIgnoreCase("Казнить"))
					System.out.println(getMessage());
			}
		}
	}

}