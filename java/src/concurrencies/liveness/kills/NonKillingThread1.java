package kills;

//Java program to illustrate non-volatile boolean flag 

public class NonKillingThread1 {

	// static used here
	// because a non-static variable
	// cannot be referenced
	// from a static context

	// exit variable to stop both
	// the main and inside threads
	static boolean exit = false;

	/***
	 * Using a volatile boolean flag: We can also use a volatile boolean flag to
	 * make our code thread safe. A volatile variable is directly stored in the
	 * main memory so that threads cannot have locally cached values of it. A
	 * situation may arise when more than one threads are accessing the same
	 * variable and the changes made by one might not be visible to other
	 * threads. In such a situation, we can use a volatile boolean flag. Note:
	 * The above code runs into an infinite loop and will give run-time error.
	 * The output above shows that the inside thread is never stopped. This
	 * happens because the change made to ‘exit’ variable in the main thread is
	 * not visible to the inside thread. This is so because the inside thread
	 * locally caches the value of exit. To prevent this from happening we can
	 * use a volatile variable.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("started main thread..");

		// a thread inside main thread
		new Thread() {
			public void run() {
				System.out.println("started inside thread..");

				// inside thread caches the value of exit,
				// so changes made to exit are not visible here
				while (!exit) // will run infinitely
				{
					// System.out.println("running ....");
					// try {
					// Thread.sleep(100);
					// } catch (InterruptedException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
				}

				// this will not be printed.
				System.out.println("exiting inside thread..");
			}
		}.start();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.out.println("Caught :" + e);
		}

		// so that we can stop the threads
		exit = true;
		System.out.println("exiting main thread..");
	}
}
