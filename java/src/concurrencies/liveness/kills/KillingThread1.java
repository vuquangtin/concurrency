package kills;

//Java program to illustrate 
//stopping a thread using boolean flag 

class MyThread implements Runnable {

	// to stop the thread
	private boolean exit;

	private String name;
	Thread t;

	MyThread(String threadname) {
		name = threadname;
		t = new Thread(this, name);
		System.out.println("New thread: " + t);
		exit = false;
		t.start(); // Starting the thread
	}

	// execution of thread starts from run() method
	public void run() {
		int i = 0;
		while (!exit) {
			System.out.println(name + ": " + i);
			i++;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Caught:" + e);
			}
		}
		System.out.println(name + " Stopped.");
	}

	// for stopping the thread
	public void stop() {
		exit = true;
	}
}

/***
 * 
 Killing threads in Java
 * 
 * A thread is automatically destroyed when the run() method has completed. But
 * it might be required to kill/stop a thread before it has completed its life
 * cycle. Previously, methods suspend(), resume() and stop() were used to manage
 * the execution of threads. But these methods were deprecated by Java 2 because
 * they could result in system failures. Modern ways to suspend/stop a thread
 * are by using a boolean flag and Thread.interrupt() method.
 * 
 * Using a boolean flag: We can define a boolean variable which is used for
 * stopping/killing threads say ‘exit’. Whenever we want to stop a thread, the
 * ‘exit’ variable will be set to true. Output:
 * 
 * New thread: Thread[First thread, 5, main] New thread: Thread[Second thread,
 * 5, main] First thread: 0 Second thread: 0 First thread: 1 Second thread: 1
 * First thread: 2 Second thread: 2 First thread: 3 Second thread: 3 First
 * thread: 4 Second thread: 4 First thread: 5 Second thread Stopped. First
 * thread Stopped. Exiting the main Thread
 * 
 * Note: The output may vary every time.
 * 
 * By using a flag we can stop a thread whenever we want to and we can prevent
 * unwanted run-time errors.
 * 
 * @author vuquangtin
 *
 */
public class KillingThread1 {
	public static void main(String args[]) {
		// creating two objects t1 & t2 of MyThread
		MyThread t1 = new MyThread("First  thread");
		MyThread t2 = new MyThread("Second thread");
		try {
			Thread.sleep(500);
			t1.stop(); // stopping thread t1
			t2.stop(); // stopping thread t2
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.out.println("Caught:" + e);
		}
		System.out.println("Exiting the main Thread");
	}
}
