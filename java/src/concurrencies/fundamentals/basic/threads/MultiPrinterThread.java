package basic.threads;

/**
 * Project: BeginningJava8LanguageFeatures FileName: MultiPrinterThread Date:
 * 2017-05-23 Time: 오전 9:16 Author: user Note: To change this template use File
 * | Settings | File Templates.
 */
public class MultiPrinterThread {
	public static void main(String[] args) {
		// Create two Thread objects
		Thread t1 = new Thread(MultiPrinterThread::print);
		Thread t2 = new Thread(MultiPrinterThread::print);
		// Start both threads
		t1.start();
		t2.start();
	}

	public static void printNomal() {
		for (int i = 1; i <= 500; i++) {
			System.out.println(i);
		}
	}

	public static synchronized void print() {
		synchronized (MultiPrinterThread.class) {
			for (int i = 1; i <= 500; i++) {
				MultiPrinterThread.class.notify();
				System.out.println(i);
				try {
					MultiPrinterThread.class.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			MultiPrinterThread.class.notifyAll();
		}

	}
}
