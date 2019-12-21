package basic.thread1;

import static java.lang.Thread.sleep;

public class SyncApp {

	public static void main(String[] args) {

		System.out.println("==App Started==");
		Printer printer = new Printer();

		// Multiple threads working on the same object
		// Sychonorize
		MyThread mRef = new MyThread(printer);
		YourThread yRef = new YourThread(printer);

		mRef.start();

		yRef.start();

		try {
			mRef.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			yRef.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("==App Finished==");
	}

}

class Printer {

	// synchronized
	void printDocuments(int numOfCopies, String docName) {
		// 干这活的时候没人可以打断

		for (int i = 1; i < numOfCopies; i++) {
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(">> Printing " + docName + " " + i);
		}
	}
}

class MyThread extends Thread {

	Printer pRef;

	MyThread(Printer p) {
		pRef = p;
	}

	public void run() {
		synchronized (pRef) {// 其他线程访问不到
			pRef.printDocuments(10, "John's.pdf");
		}
	}
}

class YourThread extends Thread {

	Printer pRef;

	YourThread(Printer p) {
		pRef = p;
	}

	public void run() {
		synchronized (pRef) {
			pRef.printDocuments(10, "Tom's.pdf");
		}
	}
}