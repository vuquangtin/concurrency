package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Java_9345_CustomThreadPoolExecutorMain {
	
	public static void main(String args[]) throws InterruptedException {
		
		new Java_9345_CustomThreadPoolExecutorMain().runnableAnonymousClassLambdaExpression();
		
		new Java_9345_CustomThreadPoolExecutorMain().newCachedThreadPoolExecutorService();
		
		new Java_9345_CustomThreadPoolExecutorMain().newFixedThreadPoolExecutorService();
		
		new Java_9345_CustomThreadPoolExecutorMain().defaultThreadFactoryThreadPoolExecutor();
		
		// call newSingleThreadExecutor() at the end
		new Java_9345_CustomThreadPoolExecutorMain().newSingleThreadExecutorExecutorService();
	}
	
	
	void runnableAnonymousClassLambdaExpression() {
		out.println("\n\nRunnable Implemention");
		
		
		Runnable runnable1 = new Runnable() {
			@Override
			public void run() {
				out.println("Annonymous Class 1");
			}
		};
		new Thread(runnable1).start();
		
		
		Runnable runnable2 = () -> {
			out.println("Replaced anonymous class using lambda expression 1");
		};
		new Thread(runnable2).start();
		
		
		Runnable thrd1 = () -> out.println("Replaced anonymous class using lambda expression 2");
		new Thread(thrd1).start();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				out.println("Anonymous class 2");
			}
		}).start();
		
		
		new Thread(() -> {
			out.println("Replaced anonymous class using lambda expression 3");
		}).start();
		
		
		new Thread(() -> out.println("Replaced anonymous class using lambda expression 4")).start();
	}

	
	void newCachedThreadPoolExecutorService(){
		out.println(" \n ******  ExecutorService using newCachedThreadPool ******");
		ExecutorService esNewCachedThreadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++) {
			Runnable RunnableWorker = new Java_9345_WorkerThread(" CachedThreadPool Task-" + i);
			esNewCachedThreadPool.execute(RunnableWorker);
		}
		esNewCachedThreadPool.shutdown();
	}
	
	
	void newFixedThreadPoolExecutorService(){
		out.println(" \n ******  ExecutorService using newFixedThreadPool  ******");
		ExecutorService esNewFixedThreadPool = Executors.newFixedThreadPool(2);
		
		// Submit work to executor service
		for (int i = 0; i < 3; i++) {
			Runnable RunnableWorker = new Java_9345_WorkerThread(" FixedThreadPool Task-" + i);
			esNewFixedThreadPool.execute(RunnableWorker);
		}
		
		// shut down executor Service
		esNewFixedThreadPool.shutdown();
		while (!esNewFixedThreadPool.isTerminated()) {
		}
	}
	
	
	void defaultThreadFactoryThreadPoolExecutor() throws InterruptedException {
		out.println("\n ******  ThreadPoolExecutor using defaultThreadFactory  ******");
		
		// RejectedExecutionHandler implementation
		Java_9345_CustomRejectedExecutionHandlerImpl rejectedExecutionHandler = new Java_9345_CustomRejectedExecutionHandlerImpl();

		// Get the ThreadFactory implementation to use
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		
		// Create ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue) & two optional parameters
		ThreadPoolExecutor executorPoolThread = new ThreadPoolExecutor(2, 4, 1, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectedExecutionHandler);

		// Start the executor pool thread
		Java_9345_CustomThreadPoolExecutor customExecutorPoolThread = new Java_9345_CustomThreadPoolExecutor(executorPoolThread, 3);
		Thread thread = new Thread(customExecutorPoolThread);
		thread.start();

		// Submit work to executor pool thread
		for (int i = 0; i < 5; i++) {
			executorPoolThread.execute(new Java_9345_WorkerThread(" DefaultThreadFactory Task" + i));
		}
		
		// shut down executor pool thread
		Thread.sleep(50);
		executorPoolThread.shutdown();
		Thread.sleep(100);
		customExecutorPoolThread.shutdown();
	}
	
	
	void newSingleThreadExecutorExecutorService(){
		out.println(" \n ******  ExecutorService using newSingleThreadExecutor  ******");
		ExecutorService esNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 3; i++) {
			Runnable RunnableWorker = new Java_9345_WorkerThread(" SingleThreadExecutor Task-" + i);
			esNewSingleThreadExecutor.execute(RunnableWorker);
		}
		esNewSingleThreadExecutor.shutdown();
	}
	
}
