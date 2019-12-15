# Java Concurrency Executors Framework Tutorial
Java executor framework allows separation of thread creation and management from the task or application logic which is executed on the thread. This is helpful in applications where multiple threads and different tasks are involved because the separation makes it possible to reuse threads and to build cleaner applications.

Java concurrency framework provides executors which use queues and thread pools to handle submitted tasks. In this tutorial, executor interfaces and implementations and configuration of executors are explained with examples.

## Executor
To execute a runnable task, instead of passing it to a thread and starting the thread, the runnable task is submitted to an Executor. Executor runs the task. Execution details such as how the tasks are maintained and the threads used for executing the tasks are not visible to the callers.

Java executor framework defines simple interface which allows for separation of code that executes a task and the task itself. The interface is Executor. It defines execute() method which takes Runnable as an argument. Following is an example of Executor implementation.

``java
public class MessageExecutor implements Executor{

	private int taskCount;
	
	@Override
	public void execute(Runnable task) {
		taskCount++;
		System.out.println("Running task "+taskCount);
		new Thread(task).start();
	}

}
```

Runnable task.

```java
public class ShowMessage implements Runnable{
	
	private String message;
	
	public ShowMessage(String msg) {
		message = msg;
	}

	@Override
	public void run() {
		System.out.println(message);
	}

}
```

Running executor.

```java
public static void main(String[] args) {
	Executor executor = new MessageExecutor();
	
	ShowMessage sm = new ShowMessage("first message");
	executor.execute(sm);
	
	sm = new ShowMessage("second message");
	executor.execute(sm);
	
	sm = new ShowMessage("third message");
	executor.execute(sm);
}
```

We have seen simple implementation of Executor which runs each submitted task in a separate thread. Java concurrency framework provides various executor implementations which can be instantiated using Executors utility class.

## ExecutorService
>The framework also defines ExecutorService interface which extends Executor interface. ExecutorService allows tracking the progress of submitted asynchronous tasks by providing Future objects to the task producer. It provides submit(), invokeAny() and invokeAll() methods for submitting the tasks. These methods return Future objects using which submitted task can be cancelled or progress can be tracked.

ExecutorService interface provides methods to shutdown the service. After calling the method shutdown(), executor service won’t accept new tasks but executes all submitted tasks. Method shutdownNow completely shuts down the service.


 
Framework provides AbstractExecutorService, ForkJoinPool and ThreadPoolExecutor implementations of ExecutorService interface. These executors can be instantiated using Executors utility class and its static methods such as newCachedThreadPool, newFixedThreadPool, newSingleThreadExecutor, newWorkStealingPool and unconfigurableExecutorService.

## ScheduledExecutorService
>ScheduledExecutorService extends ExecutorService interface. In addition to running tasks concurrently, it supports task scheduling. Tasks can be scheduled to run in regular intervals or after expiry of specified duration. It provides schedule and sheduleAtFixtedRate methods for scheduling tasks.

Framework provides ScheduledThreadPoolExecutor an implementation of ScheduledExecutorService , which can be instantiated using Executor utility class and its static methods such as newScheduledThreadPool, newSingleThreadScheduledExecutor and unconfigurableScheduledExecutorService.

## ThreadPoolExecutor
>ThreadPoolExecutor is an impelementation of ExecutorService interface. Tasks submitted to it are queued and executed on the available thread from the thread pool it maintains. You can configure the ThreadPoolExecutor object by passing values for corePoolSize, maximumPoolSize and keepAliveTime properties and passing ThreadFactory and BlockingQueue objects to its constructor. You can also set these properties using corresponding setter methods.

Property corePoolSize size specifies the maximum numbers of idle threads which can exist in the pool. Property maximumPoolSize specifies maximum number of threads the pool can contain. Property keepAliveTime specifies the maximum idle time after which excess, than core pool size, idle threads will be terminated. ThreadFactory object is used to create threads and BlockingQueue object is used for queuing tasks submitted to the executor.


 
After creating ThreadPoolExecutor instance, you can execute or submit task. Using execute method, status of the submitted tasks can’t be tracked. Method submit returns Future object which can be used to track the status by calling isDone() method or cancel the task by calling cancel() method.

```java
		//ThreadFactory
		ThreadFactory thf = new ThreadFactory() {
			@Override
			public Thread newThread(Runnable runnable) {
					return new Thread(runnable);
			}			
		};
		
		//instantiate ThreadPoolExecutor
		ExecutorService executor = new ThreadPoolExecutor(5, 
				10, 1000, TimeUnit.MILLISECONDS,
				new PriorityBlockingQueue<Runnable>(), thf);


		ShowMessage sm = new ShowMessage("first message");
		//submit runnable
		Future<?> f = executor.submit(sm);
		if(f.isDone()){
			System.out.println("task complete");
		}

		sm = new ShowMessage("second message");
		executor.execute(sm);

		//shutdown 
		if(!executor.isShutdown())
			executor.shutdown();
```

Executor needs to be shoutdown in the end. You can check the executor status by using isShutdown and isTerminated methods. You can specify a handler that will run for each task that can’t be handled by the executor. The task rejection handler can be set by calling setRejectedExecutionHandler method and passing RejectedExecutionHandler object such as ThreadPoolExecutor.AbortPolicy, ThreadPoolExecutor.CallerRunsPolicy, ThreadPoolExecutor.DiscardOldestPolicy, ThreadPoolExecutor.DiscardPolicy.

```java
	executor.setRejectedExecutionHandler(
			new ThreadPoolExecutor.DiscardPolicy());
```

You can use Executors utility class’s static methods such as newCachedThreadPool, newFixedThreadPool, newWorkStealingPool and newSingleThreadExecutor to obtain configured ExecutorService object. Thread pool of ExecutorService returned by newCachedThreadPool method creates new thread as needed, reuses threads if available and removes idle threads. ExecutorService returned by newCachedThreadPool is useful in executing short lived asynchronous tasks.

```java
ExecutorService executor = Executors.newCachedThreadPool();
```

ExecutorService returned by newSingleThreadExecutor uses single thread to execute tasks and maintains queue to keep pending tasks.

ExecutorService returned by newFixedThreadPool maintains thread pool which reuses fixed number of threads, uses queue for queuing tasks and executes them as threads available.


 
ExecutorService returned by newWorkStealingPool maintains thread pool which creates enough threads to maintain the specified parallelism and the order in which queued tasks are executed is not maintained.

## ScheduledThreadPoolExecutor
ScheduledThreadPoolExecutor extends ThreadPoolExecutor and implements ScheduledExecutorService interface. In addition to all the methods inherited from ThreadPoolExecutor, ScheduledThreadPoolExecutor contains methods which allow scheduling tasks.

ScheduledThreadPoolExecutor can be instantiated by passing value for corePoolSize property and optional ThreadFactory. You can schedule a task which will be executed after a specified delay using schedule() method.

Methods scheduleAtFixedRate and scheduleWithFixedDelay can be used to schedule tasks which will be executed periodically.

Following example shows how to use ScheduledThreadPoolExecutor to schedule tasks which run after delay and in regular intervals.

```java
	//instantiate ScheduledThreadPoolExecutor
	//with core pool size
	int corePoolSize = 5;
	ScheduledThreadPoolExecutor executor = 
			new ScheduledThreadPoolExecutor(corePoolSize);


	ShowMessage sm = new ShowMessage("first message");

	//schedule a task to be run after delay 
	int delay = 1000;
	executor.schedule(sm, delay, TimeUnit.MILLISECONDS);

	sm = new ShowMessage("second message");
	
	//run task periodically 
	int initDelay = 200;
	int period = 2000;
	executor.scheduleAtFixedRate(sm, initDelay, 
			period, TimeUnit.MILLISECONDS);
```

You can create ScheduledThreadPoolExecutor object using Executors class’s static method such as newScheduledThreadPool and newSingleThreadScheduledExecutor.

## Callable
>ExecutorService and ScheduledExecutorService interfaces accept callable tasks. Callable tasks are created by implementing Callable interface and its method call(). Callable interface is similar to Runnable but it can return result and throw an exception if the task can’t be run.

When a callable is submitted to ExecutorService, the returned Future object can be used to capture result from the callable. Executors utility class can be used to convert Runnable object to Callable object.

Callable implementation example.

```java
public class NewsCallable implements Callable<String>{
	@Override
	public String call() throws Exception {
		//get latest news from remote service
		return "execellent news";
	}
}
```

Following example shows submitting callable task to executor service and getting results from it.

```java
	ExecutorService executor = Executors.newCachedThreadPool();

	NewsCallable callable = new NewsCallable();

	//run a callable
	Future<String> future = executor.submit(callable);

	try {	
		while(!future.isDone()) {
			Thread.sleep(20);
		}

		String result = future.get();
		System.out.println("result from callable "+result);
	}catch (Exception e) {
		e.printStackTrace();
	}
	executor.shutdown();
```

Following example shows how to schedule a callable task.

```java
	ScheduledExecutorService executor = Executors
			.newScheduledThreadPool(4);

	NewsCallable callable = new NewsCallable();

	//schedule to run callable task
	ScheduledFuture<String> future = executor.schedule(callable, 
			1000, TimeUnit.MILLISECONDS);

	try {	
		Thread.sleep(future.getDelay(TimeUnit.MILLISECONDS));
		
		while(!future.isDone()) {
			Thread.sleep(20);
		}

		String result = future.get();
		System.out.println("result from callable "+result);
	}catch (Exception e) {
		e.printStackTrace();
	}
	executor.shutdown();
```

## CompletionService
>Using CompletionService, you can separate the task producer from result consumer. ExecutorCompletionService is an implementation of CompletionService interface. It can be instantiated passing an executor service which executes the submitted tasks.

>Without using the completion service, task submitter immediately gets the future object after submitting a task. With CompletionService, task submitter just submits the task and result processer can poll for completed tasks and get results. CompletionService has two methods, take() and get(), for getting results of the completed task.


 
Following examples shows how to use ExecutorCompletionService. The example produces tasks for getting best offer of a given store. The tasks are submitted to ExecutorCompletionService in one thread and main thread gets future object form the completion service and processes the results.

```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class StoreCouponFetcher {

	private int taskCounter;

	public static void main(String[] args) {
		StoreCouponFetcher scf = new StoreCouponFetcher();
		scf.processCoupons();
	}
	
	public void processCoupons() {
		
		//executor service
		ExecutorService executor = Executors.newCachedThreadPool();
		
		//completion service
		ExecutorCompletionService<TaskResult> completionService =
				new ExecutorCompletionService<TaskResult>(executor);
		
		//task producer
		new Thread(new CouponTaskProducer(
				completionService, getStores())).start();
		
		//task result consumer
		do {
			try {
				Future<TaskResult> future = completionService.take();
				TaskResult taskResult = future.get();
				System.out.println(taskResult.getTaskOwner()+" "
								+taskResult.getResult());
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			taskCounter--;
		}while(taskCounter > 0);

		executor.shutdown();
	}
	
	public List<String> getStores() {
		List<String> lst = new ArrayList<>();
		lst.add("amazon");
		lst.add("bestbuy");
		lst.add("ebay");
		lst.add("macys");
		lst.add("jcpenny");
		
		taskCounter = lst.size();
		
		return lst;
	}
	
	static class CouponCallable implements Callable<TaskResult>{
		
		private String store;
		
		public CouponCallable(String store) {
			this.store = store;
		}

		@Override
		public TaskResult call() throws Exception {
			TaskResult tr = new TaskResult();
			tr.setTaskOwner(store);
			
			//get offer from remote service
			int dicount = (int )(Math.random() * 90);
			
			tr.setResult("get flat "+dicount+ "% off");
			
			return tr;
		}
		
	}
	
	static class CouponTaskProducer implements Runnable{
		
		private ExecutorCompletionService<TaskResult> completionService;
		private List<String> stores;
		
		public CouponTaskProducer(
				ExecutorCompletionService<TaskResult> ecs, 
				List<String> stores) {
			
			completionService = ecs;
			this.stores = stores;
		}
		@Override
		public void run() {
			
			for(String store : stores) {
				CouponCallable callable = new CouponCallable(store);
				completionService.submit(callable);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {}
			}				
		}
	}
}
```
