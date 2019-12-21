package cookbook.chapter1.threadmanagement.recipe05;

import java.util.concurrent.TimeUnit;

/**
 * Main class of the Example. Creates a FileClock runnable object
 * and a Thread to run it. Starts the Thread, waits five seconds
 * and interrupts it. 
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Creates a FileClock runnable object and a Thread
		// to run it
		FileClock clock=new FileClock();
		Thread thread=new Thread(clock);
		
		// Starts the Thread
		thread.start();
		try {
			// Waits five seconds
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
		// Interrupts the Thread
		thread.interrupt();
	}
}
