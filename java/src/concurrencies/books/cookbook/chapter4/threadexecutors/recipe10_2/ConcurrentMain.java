package cookbook.chapter4.threadexecutors.recipe10_2;

public final class ConcurrentMain {

	/**
	 * The difference between Normal Executor Service and Completion Service is
	 * visible at runtime, Normal Executor Service always waits for the next
	 * thread to finish the job before printing the number, the client needs to
	 * wait until a big bunch of threads return all together. Completion Service
	 * is behaving differently, it follows a producer/consumer philosophy: as
	 * soon a thread is done, it puts the result into a non blocking queue so
	 * that the consumer can take it.
	 * 
	 * @see https
	 *      ://github.com/smazumder05/java8-examples/blob/master/src/main/java
	 *      /sm/concurrent/ConcurrentMain.java
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println("Running Normal Loop");
		long start = System.currentTimeMillis();
		System.out.println("Running Normal Loop");
		new Completion().useNormalLoop();
		System.out.println();
		System.out.println("Execution time : "
				+ (System.currentTimeMillis() - start));
		System.out.println("Running Normal Executor");
		new Completion().normalExecutor();
		System.out.println();
		System.out.println("Execution time : "
				+ (System.currentTimeMillis() - start));
		System.out.println("Running Completion Executor");
		new Completion().completionService();
		System.out.println();
		System.out.println("Execution time : "
				+ (System.currentTimeMillis() - start));
	}
}