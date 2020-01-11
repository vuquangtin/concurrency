# My Custom Thread Pool Executor in Java

ThreadPoolExecutor is a feature added by java concurrent api to maintain and reuse threads efficiently , so that our programs don’t have to worry about creating and destroying threads and focus on the core functionality. I have created a custom thread pool executor to get better understanding of how thread pool executor would work .

Functionality :

 * It maintains a fixed thread pool ,and creates threads and start the threads even if no task is submitted whereas ThreadPoolExecutor creates threads on demand , i.e. whenever a runnable is submitted to pool and the number of threads are less than core pool size .
 * In ThreadPoolExecutor, we provide a waiting queue ,where new runnable task waits when all threads are busy running existing task. Once the queue is filled , new threads will be created up to maximum pool size. In MyThreadPool , i am storing the runnable in a linked list , so every task will wait in the list and it is unbounded , so no usage of maxPoolSize in this .
 * In ThreadPoolExecutor , we use Future Objects to get the result from task , future.get() method will block if the result is not available , or we use CompletionService . In MyThreadPoolExecutor , i have created a simple interface called ResultListener , user has to provide a implementation of this as to how he wants the output to be processed . After every task is completed , the ResultListener will get callback with the output of task or error method will be called in case of any exception.
 * When shutdown method is called , MyThreadPoolExecutor will stop accepting new tasks and complete the remaining tasks .
 * I have provided very basic functionality as compared to ThreadPoolExecutor , i have used simple thread mechanism like wait() , notify() , notifyAll(), and join().
 * Performance wise it is similar to ThreadPoolExecutor , some times better in some cases. Do let me know if you find any interesting results or ways to improve it .
 
```java
package threadpools.mycustom;

import java.util.concurrent.Callable;

/**
 * Run submitted task of {@link MyThreadPool} After running the task , It calls
 * on {@link ResultListener}object with {@link Output}which contains returned
 * result of {@link Callable}task. Waits if the pool is empty.
 *
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyThread<V> extends Thread {
	/**
	 * MyThreadPool object, from which the task to be run
	 */
	private MyThreadPool<V> pool;
	private boolean active = true;

	public boolean isActive() {
		return active;
	}

	public void setPool(MyThreadPool<V> p) {
		pool = p;
	}

	/**
	 * Checks if there are any unfinished tasks left. if there are , then runs
	 * the task and call back with output on resultListner Waits if there are no
	 * tasks available to run If shutDown is called on MyThreadPool, all waiting
	 * threads will exit and all running threads will exit after finishing the
	 * task
	 */
	public void run() {
		ResultListener<V> result = pool.getResultListener();
		Callable<V> task;
		while (true) {
			task = pool.removeFromQueue();
			if (task != null) {
				try {
					V output = task.call();
					result.finish(output);
				} catch (Exception e) {
					result.error(e);
				}
			} else {
				if (!isActive())
					break;
				else {
					synchronized (pool.getWaitLock()) {
						try {
							pool.getWaitLock().wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	void shutdown() {
		active = false;
	}
}

```

```java
package threadpools.mycustom;

import java.util.LinkedList;
import java.util.concurrent.Callable;

/**
 * This class is used to execute submitted {@link Callable} tasks. this class
 * creates and manages fixed number of threads User will provide a
 * {@link ResultListener}object in order to get the Result of submitted task
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyThreadPool<V> {
	private Object waitLock = new Object();

	public Object getWaitLock() {
		return waitLock;
	}

	/**
	 * list of threads for completing submitted tasks
	 */
	private final LinkedList<MyThread<V>> threads;
	/**
	 * submitted task will be kept in this list untill they run by one of
	 * threads in pool
	 */
	private final LinkedList<Callable<V>> tasks;
	/**
	 * shutDown flag to shut Down service
	 */
	private volatile boolean shutDown;
	/**
	 * ResultListener to get back the result of submitted tasks
	 */
	private ResultListener<V> resultListener;

	/**
	 * initializes the threadPool by starting the threads threads will wait till
	 * tasks are not submitted
	 *
	 * @param size
	 *            Number of threads to be created and maintained in pool
	 * @param myResultListener
	 *            ResultListener to get back result
	 */
	public MyThreadPool(int size, ResultListener<V> myResultListener) {
		tasks = new LinkedList<Callable<V>>();
		threads = new LinkedList<MyThread<V>>();
		shutDown = false;
		resultListener = myResultListener;
		for (int i = 0; i < size; i++) {
			MyThread<V> myThread = new MyThread<V>();
			myThread.setPool(this);
			threads.add(myThread);
			myThread.start();
		}
	}

	public ResultListener<V> getResultListener() {
		return resultListener;
	}

	public void setResultListener(ResultListener<V> resultListener) {
		this.resultListener = resultListener;
	}

	public boolean isShutDown() {
		return shutDown;
	}

	public int getThreadPoolSize() {
		return threads.size();
	}

	public synchronized Callable<V> removeFromQueue() {
		return tasks.poll();
	}

	public synchronized void addToTasks(Callable<V> callable) {
		tasks.add(callable);
	}

	/**
	 * submits the task to threadPool. will not accept any new task if shutDown
	 * is called Adds the task to the list and notify any waiting threads
	 *
	 * @param callable
	 */
	public void submit(Callable<V> callable) {
		if (!shutDown) {
			addToTasks(callable);
			synchronized (this.waitLock) {
				waitLock.notify();
			}
		} else {
			System.out.println("task is rejected.. Pool shutDown executed");
		}
	}

	/**
	 * Initiates a shutdown in which previously submitted tasks are executed,
	 * but no new tasks will be accepted. Waits if there are unfinished tasks
	 * remaining
	 *
	 */
	public void stop() {
		for (MyThread<V> mythread : threads) {
			mythread.shutdown();
		}
		synchronized (this.waitLock) {
			waitLock.notifyAll();
		}
		for (MyThread<V> mythread : threads) {
			try {
				mythread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

```

```java
package threadpools.mycustom;

/**
 * 
 * This interface imposes finish method which is used to get the {@link Output}
 * object of finished task
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public interface ResultListener<T>
{

	public void finish(T obj);

	public void error(Exception ex);

}

```

you can implement this class as you want to get back and process the result returned by tasks.

```java
package threadpools.mycustom;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class DefaultResultListener implements ResultListener<Object> {

	@Override
	public void finish(Object obj) {

	}

	@Override
	public void error(Exception ex) {
		ex.printStackTrace();
	}

}

```
For example this class will add the number returned by tasks .

```java
package threadpools.mycustom;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ResultListener class to keep track of total matched count
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class MatchedCountResultListener<V> implements ResultListener<V>

{

	/**
	 * matchedCount to keep track of the number of matches returned by submitted
	 * task
	 */
	AtomicInteger matchedCount = new AtomicInteger();

	/**
	 * this method is called by ThreadPool to give back the result of callable
	 * task. if the task completed successfully then increment the matchedCount
	 * by result count
	 */
	@Override
	public void finish(V obj) {
		// System.out.println('count is '+obj);
		matchedCount.addAndGet((Integer) obj);
	}

	/**
	 * print exception thrown in running the task
	 */
	@Override
	public void error(Exception ex) {
		ex.printStackTrace();
	}

	/**
	 * returns the final matched count of all the finished tasks
	 * 
	 * @return
	 */
	public int getFinalCount() {
		return matchedCount.get();
	}
}

```
This is a test class which runs simple for loop using CompletionService and MyThreadPoolExecutor

```java
package threadpools.mycustom;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is a test class which runs simple for loop using CompletionService and
 * MyThreadPoolExecutor
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class TestClass {

	public static void main(String[] args) throws InterruptedException {
		CompletionService<Integer> threadService;
		ExecutorService service = Executors.newFixedThreadPool(2);
		threadService = new ExecutorCompletionService<Integer>(service);

		long b = System.currentTimeMillis();
		for (int i = 0; i < 50000; i++) {
			threadService.submit(new MyRunable(i));
		}

		service.shutdown();
		System.out.println("time taken by Completion Service " + (System.currentTimeMillis() - b));

		ResultListener<Integer> result = new DefaultResultListener<Integer>();
		MyThreadPool<Integer> newPool = new MyThreadPool<Integer>(2, result);
		long a = System.currentTimeMillis();

		int cc = 0;
		for (int i = 0; i < 50000; i++) {
			cc = cc + i;
		}
		System.out.println("time taken without any pool " + (System.currentTimeMillis() - a));
		a = System.currentTimeMillis();

		for (int i = 0; i < 5000; i++) {
			newPool.submit(new MyRunable(i));
		}

		newPool.stop();
		System.out.println("time taken by myThreadPool " + (System.currentTimeMillis() - a));
	}

}

class MyRunable implements Callable<Integer>

{
	int index = -1;

	public MyRunable(int index) {
		this.index = index;
	}

	@Override
	public Integer call() throws Exception {
		return index;
	}

}
```

