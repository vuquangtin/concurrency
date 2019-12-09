package blockingqueues;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FinderMain {
	private BlockingQueue<byte[]> blockingQueue = new ArrayBlockingQueue<byte[]>(
			10);
	private static ExecutorService service;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		service = Executors.newFixedThreadPool(10);
		FinderMain finderMain = new FinderMain();
		finderMain.startFind(service);
		service.shutdown();

	}

	public void startFind(ExecutorService service) {
		Finder finder;

		for (int i = 0; i < 5; ++i) { // Имитация нахождения 5 файлов в
										// директории :))
			finder = new Finder("out" + i + ".txt");
			service.submit(finder);
		}
	}

	class Finder extends Thread {
		private Producer producer;
		private Consumer consumer;

		public Finder(String fileName) {
			// TODO Auto-generated constructor stub
			consumer = new Consumer();
			producer = new Producer(fileName);

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			producer.start();
			consumer.start();
		}

	}

	class Producer extends Thread {
		private String fileName;

		public Producer(String fileName) {
			// TODO Auto-generated constructor stub
			this.fileName = fileName;

		}

		@Override
		public void run() {

			FileInputStream fileInputStream;
			byte[] buffer = new byte[64];
			try {
				fileInputStream = new FileInputStream(fileName);
				int size_of_read = 0;

				while ((size_of_read = fileInputStream.read(buffer)) != -1) {
					try {
						blockingQueue.put(buffer);
						System.out.println(size_of_read);
					} catch (InterruptedException e) {
						// TODO: handle exception
					}
					buffer = new byte[64];
				}
				// syncConsumer.notify();
				this.interrupt();
				// blockingQueue.clear();

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

	}

	class Consumer extends Thread {

		@Override
		public void run() {

			try {

				while (true) {
					System.out.println(new String(blockingQueue.take()));
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}