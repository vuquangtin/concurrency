package running.threads.in.sequence;

/***
 * Java program to print numbers using threads and wait notify is very similar
 * to the example above except that there is a condition for wait now
 * (while(number % 3 != threadNumber)). Unless the assigned thread number is not
 * equal to the remainder of the number division by 3, thread is made to wait.
 * 
 * Only when the assigned thread number is equal to the remainder of the number
 * division thread prints the number and notifies other waiting threads so that
 * one of them can enter the synchronized block.
 * 
 * Another interesting thing in the code is this condition- while (number <
 * PrintNumbers.MAX_NUMBERS – 2). Why check for two less than the required
 * number? It is because loop will anyway run for all the three threads and for
 * value 8 itself one thread will increment it to 9 and another to 10.
 * 
 * @author admin
 *
 */
public class PrintNumbers2 {
	final static int MAX_NUMBERS = 10;

	public static void main(String[] args) {
		// shared object
		PrintNumbers obj = new PrintNumbers();
		// Creating 3 threads
		Thread t1 = new Thread(new NumberRunnable2(obj, 0), "T1");
		Thread t2 = new Thread(new NumberRunnable2(obj, 1), "T2");
		Thread t3 = new Thread(new NumberRunnable2(obj, 2), "T3");
		t1.start();
		t2.start();
		t3.start();
	}
}

class NumberRunnable2 implements Runnable {
	PrintNumbers obj;
	int threadNumber;
	static volatile int number = 0;

	NumberRunnable2(PrintNumbers obj, int result) {
		this.obj = obj;
		this.threadNumber = result;
	}

	@Override
	public void run() {

		synchronized (obj) {
			while (number < PrintNumbers.MAX_NUMBERS - 2) {
				while (number % 3 != threadNumber) {
					try {
						obj.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread().getName() + " - " + ++number);
				obj.notifyAll();
			}
		}
	}
}