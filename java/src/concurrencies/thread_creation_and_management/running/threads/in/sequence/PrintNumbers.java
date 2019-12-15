package running.threads.in.sequence;

/***
 * Print Numbers Sequentially Using Three Threads in Java This post shows how
 * you can write a program in Java to print numbers in sequence using three
 * threads. If there are three threads T1, T2, T3 then numbers should be printed
 * alternatively by these threads in the following manner-
 * 
 * T1- 1 T2- 2 T3- 3 T1- 4 T2- 5 T3- 6 .. .. ..
 * 
 * Printing numbers using 3 threads – Java program In the program each thread is
 * assigned a number (0, 1 and 2 respectively). Before thread prints a number,
 * each number is divided by 3 and the thread which has an assigned number equal
 * to the remainder of this division is eligible to print the number.
 * 
 * For example-
 * 
 * if number % 3 ==0 Then T1 prints the number if number % 3 ==1 Then T2 prints
 * the number if number % 3 ==2 Then T3 prints the number
 * 
 * This program for printing numbers in sequence using threads can be written
 * using only synchronized keyword or it can be written using wait, notify,
 * notifyAll methods for inter thread communication.
 * 
 * 
 * @author admin
 *
 */
public class PrintNumbers {
	final static int MAX_NUMBERS = 10;

	public static void main(String[] args) {
		// shared object
		PrintNumbers obj = new PrintNumbers();
		// Creating 3 threads
		Thread t1 = new Thread(new NumberRunnable(obj, 0), "T1");
		Thread t2 = new Thread(new NumberRunnable(obj, 1), "T2");
		Thread t3 = new Thread(new NumberRunnable(obj, 2), "T3");
		t1.start();
		t2.start();
		t3.start();
	}
}

class NumberRunnable implements Runnable {
	PrintNumbers obj;
	int threadNumber;
	static int number = 0;

	NumberRunnable(PrintNumbers obj, int result) {
		this.obj = obj;
		this.threadNumber = result;
	}

	@Override
	public void run() {
		while (number < PrintNumbers.MAX_NUMBERS) {
			synchronized (obj) {
				// check again for (number < PrintNumbers.MAX_NUMBERS) otherwise
				// one more number my be
				// printed by another thread
				if (number % 3 == threadNumber && number < PrintNumbers.MAX_NUMBERS) {
					System.out.println(Thread.currentThread().getName() + " - " + ++number);
				}
			}
		}
	}
}