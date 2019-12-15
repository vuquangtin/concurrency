package running.threads.in.sequence;

/***
 * Printing Odd and Even Numbers Alternatively 5.1. Using wait() and notify()
 * 
 * We will use the discussed concepts of synchronization and inter-thread
 * communication to print odd and even numbers in ascending order using two
 * different threads.
 * 
 * In the first step, we’ll implement the Runnable interface to define the logic
 * of both threads. In the run method, we check if the number is even or odd.
 * 
 * If the number is even, we call the printEven method of the Printer class,
 * else we call the printOdd method:
 * 
 * 
 * The first thread will be the odd thread, hence we pass false as the value of
 * the parameter isEvenNumber. For the second thread, we pass true instead. We
 * set the maxValue to 10 for both threads, so that only the numbers from 1
 * through 10 are printed.
 * 
 * We then start both the threads by calling the start() method. This will
 * invoke the run() method of both threads as defined above wherein we check if
 * the number is odd or even and print them.
 * 
 * When the odd thread starts running, the value of the variable number will be
 * 1. Since it is less than the maxValue and the flag isEvenNumber is false,
 * printOdd() is called. In the method, we check if the flag isOdd is true and
 * while it is true we call wait(). Since isOdd is false initially, wait() is
 * not called, and the value is printed.
 * 
 * We then set the value of isOdd to true, so that the odd thread goes into the
 * wait state and call notify() to wake up the even thread. The even thread then
 * wakes up and prints the even number since the odd flag is false. It then
 * calls notify() to wake up the odd thread.
 * 
 * The same process is carried out until the value of the variable number is
 * greater than the maxValue.
 * 
 * @author admin
 *
 */
class TaskEvenOdd implements Runnable {
	private int max;
	private Printer print;
	private boolean isEvenNumber;

	// standard constructors

	public TaskEvenOdd(Printer print, int max, boolean isEvenNumber) {
		this.print = print;
		this.max = max;
		this.isEvenNumber = isEvenNumber;
	}

	@Override
	public void run() {
		int number = isEvenNumber ? 2 : 1;
		while (number <= max) {
			if (isEvenNumber) {
				print.printEven(number);
			} else {
				print.printOdd(number);
			}
			number += 2;
		}
	}
}

class Printer {
	private volatile boolean isOdd;

	synchronized void printEven(int number) {
		while (!isOdd) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		System.out.println(Thread.currentThread().getName() + ":" + number);
		isOdd = false;
		notify();
	}

	synchronized void printOdd(int number) {
		while (isOdd) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		System.out.println(Thread.currentThread().getName() + ":" + number);
		isOdd = true;
		notify();
	}
}

public class UsingWaitAndNotify {
	public static void main(String... args) {
		Printer print = new Printer();
		Thread t1 = new Thread(new TaskEvenOdd(print, 10, false), "Odd");
		Thread t2 = new Thread(new TaskEvenOdd(print, 10, true), "Even");
		t1.start();
		t2.start();
	}
}
