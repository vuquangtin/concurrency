package running.threads.in.sequence;

import java.util.concurrent.Semaphore;

/***
 * A semaphore controls access to a shared resource through the use of a
 * counter. If the counter is greater than zero, then access is allowed. If it
 * is zero, then access is denied.
 * 
 * Java provides the Semaphore class in the java.util.concurrent package and we
 * can use it to implement the explained mechanism. More details about
 * semaphores can be found here.
 * 
 * We create two threads, an odd thread, and an even thread. The odd thread
 * would print the odd numbers starting from 1, and the even thread will print
 * the even numbers starting from 2.
 * 
 * Both the threads have an object of the SharedPrinter class. The SharedPrinter
 * class will have two semaphores, semOdd and semEven which will have 1 and 0
 * permits to start with. This will ensure that odd number gets printed first.
 * 
 * We have two methods printEvenNum() and printOddNum(). The odd thread calls
 * the printOddNum() method and the even thread calls the printEvenNum() method.
 * 
 * To print an odd number, the acquire() method is called on semOdd, and since
 * the initial permit is 1, it acquires the access successfully, prints the odd
 * number and calls release() on semEven.
 * 
 * Calling release() will increment the permit by 1 for semEven, and the even
 * thread can then successfully acquire the access and print the even number.
 * 
 * This is the code for the workflow described above:
 * 
 * @author admin
 *
 */
public class UsingSemaphores {
	public static void main(String[] args) {
		SharedPrinter sp = new SharedPrinter();
		Thread odd = new Thread(new Odd(sp, 10), "Odd");
		Thread even = new Thread(new Even(sp, 10), "Even");
		odd.start();
		even.start();
	}
}
/***
 * @see https://www.geeksforgeeks.org/producer-consumer-solution-using-semaphores-java/
 * @author admin
 *
 */
class SharedPrinter {

	private Semaphore semEven = new Semaphore(-3);
	private Semaphore semOdd = new Semaphore(1);

	void printEvenNum(int num) {
		try {
			System.out.println("printEvenNum:"+semEven.availablePermits());
			semEven.acquire();
			//semEven.acquire();
			//semEven.acquire();
			System.out.println("printEvenNum:"+semEven.availablePermits());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.out.println(Thread.currentThread().getName() + num);
		semOdd.release();
	}

	void printOddNum(int num) {
		try {
			System.out.println(semEven.availablePermits());
			semOdd.acquire();
			System.out.println(semOdd.availablePermits());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.out.println(Thread.currentThread().getName() + num);
		semEven.release(4);
		//semEven.release();
		//semEven.release();
		//semEven.release();

	}
}

class Even implements Runnable {
	private SharedPrinter sp;
	private int max;

	// standard constructor

	public Even(SharedPrinter sp, int max) {
		this.sp=sp;
		this.max=max;
	}

	@Override
	public void run() {
		for (int i = 2; i <= max; i = i + 2) {
			sp.printEvenNum(i);
		}
	}
}

class Odd implements Runnable {
	private SharedPrinter sp;
	private int max;

	public Odd(SharedPrinter sp, int max) {
		this.sp=sp;
		this.max=max;
	}

	// standard constructors
	@Override
	public void run() {
		for (int i = 1; i <= max; i = i + 2) {
			sp.printOddNum(i);
		}
	}
}
