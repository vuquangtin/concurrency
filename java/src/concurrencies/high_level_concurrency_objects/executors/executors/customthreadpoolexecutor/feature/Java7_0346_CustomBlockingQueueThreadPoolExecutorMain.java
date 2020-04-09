package executors.customthreadpoolexecutor.feature;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Java7_0346_CustomBlockingQueueThreadPoolExecutorMain {
	public static void main(String[] args) {
		Integer threadCounter = 0;
		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(50);
		Java7_0346_CustomBlockingQueueThreadPoolExecutor executor = new Java7_0346_CustomBlockingQueueThreadPoolExecutor(10, 20, 5000, TimeUnit.MILLISECONDS, blockingQueue);
		
		executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				System.out.println("DemoTask Rejected : " + ((Java7_0346_CustomBlockingQueueThreadPoolExecutorWorkerThread) r).getName());
				System.out.println("Waiting for a second !!");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Lets add another time : " + ((Java7_0346_CustomBlockingQueueThreadPoolExecutorWorkerThread) r).getName());
				executor.execute(r);
			}
		});
		
		// Let start all core threads initially
		executor.prestartAllCoreThreads();
		while (true) {
			threadCounter++;
			// Adding threads one by one
			System.out.println("Adding DemoTask : " + threadCounter);
			executor.execute(new Java7_0346_CustomBlockingQueueThreadPoolExecutorWorkerThread(threadCounter.toString()));
			if (threadCounter == 100)
				break;
		}
	}

}
