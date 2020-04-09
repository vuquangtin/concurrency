package executors.customthreadpoolexecutor.my;

/**
 * A custom ThreadPoolExecutor implemented in Java Source contents
 * MyThreadPoolExecutor This is the class responsible for handling the tasks
 * execution and threads management.
 * 
 * MyThread A class that extends the Java Thread class. These are the threads
 * that are pooled and execute the tasks.
 * 
 * MyTask A class that implements the Java Runnable interface. These are the
 * tasks that are executed by the threads. An override for the run method is
 * present, as well as a success handler.
 * 
 * MyTaskQueue Basically this is an ArrayList of the tasks that do not have any
 * available threads. Common actions such as get, put, isEmpty and isFull are
 * implemented.
 * 
 * Walkthrough A MyThreadPoolExecutor instance is created, with given
 * CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME and QUEUE_SIZE. Then, a
 * NUMBER_OF_TASKS are passed to the executor, one by one. If the executor
 * refuses task, then the task is passed to the executor, until the executor
 * accepts it.
 * 
 * Given the time when tasks are passed to the executor, the threadPool gains in
 * size. When it reaches the CORE_POOL_SIZE number of threads, the new tasks are
 * put in the task queue. If the task queue is full, any new task forces a
 * creation of a new thread, until the MAXIMUM_POOL_SIZE number of threads is
 * reached. At that point, MyThreadPoolExecutor will reject the task.
 * 
 * The internals of MyThread are pretty self explanatory. While the thread pool
 * executor is active, the thread will keep on getting new tasks. The thread
 * starts with the task given to it at the initialization. After it finished
 * that task, it tries to get and run tasks from the task queue. If the thread
 * does not have any tasks to run, it will wait for KEEP_ALIVE_TIME milliseconds
 * (extended thread) or indefinitely (core thread), until the thread pool
 * executor notifies it that a new task is in the queue. If the waiting time
 * expires or a notify signal is issued by the executor, the thread checks again
 * if there are any tasks left in the task queue. If the task queue is empty,
 * the thread is removed from the thread pool.
 * 
 * When there are no more threads in the thread pool, a destroy signal is sent
 * to the thread pool executor and the application finishes.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 * @see <a href="https://github.com/vladmihaisavin/java-thread-pool-executor">
 *      https://github.com/vladmihaisavin/java-thread-pool-executor</a>
 *
 */
public class Main {

	public static long startTime = System.currentTimeMillis();
	private static final int CORE_POOL_SIZE = 3;
	private static final int MAXIMUM_POOL_SIZE = 5;
	private static final int KEEP_ALIVE_TIME = 1000;
	private static final int QUEUE_SIZE = 3;
	private static final int NUMBER_OF_TASKS = 200;

	private static void reject(int id, double end) {
		System.out.println("Task no. " + id + " rejected at " + end);
	}

	public static void main(String[] args) throws InterruptedException {
		MyThreadPoolExecutor threadPoolExecutor = new MyThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
				KEEP_ALIVE_TIME, QUEUE_SIZE);

		for (int i = 0; i <= NUMBER_OF_TASKS; ++i) {
			boolean success = false;
			while (!success) {
				if (!threadPoolExecutor.execute(new MyTask(i))) {
					double end = System.currentTimeMillis() - startTime;
					reject(i, end);
				} else {
					success = true;
				}
				Thread.sleep(10);
			}
		}
		boolean threadPoolIsActive = true;
		while (threadPoolIsActive) {
			if (threadPoolExecutor.getThreadPoolSize() == CORE_POOL_SIZE) {
				threadPoolExecutor.destroy();
				threadPoolIsActive = false;
			}
			Thread.sleep(100);
		}
	}
}