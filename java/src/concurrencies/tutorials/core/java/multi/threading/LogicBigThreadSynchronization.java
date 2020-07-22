package core.java.multi.threading;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

import core.java.base.ExtendTemplateMethod;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class LogicBigThreadSynchronization extends ExtendTemplateMethod {

	public static void main(String[] args) {
		new LogicBigThreadSynchronization().runTemplateMethod(args);

	}

	private Integer counter = 0;

	/****
	 * Java - Thread interference, Race Condition and Synchronization
	 * 
	 * In a multithreaded application, when multiple threads access and modify
	 * the 'same data', the behavior cannot always be predictable.
	 * 
	 * To understand the problem let's see an example:
	 */
	@Override
	public void implementionOne(String[] args) throws Exception {
		LogicBigThreadSynchronization demo = new LogicBigThreadSynchronization();
		Task task1 = demo.new Task();
		Thread thread1 = new Thread(task1);

		Task task2 = demo.new Task();
		Thread thread2 = new Thread(task2);

		thread1.start();
		thread2.start();
		/*
		 * Output:
		 * 
		 * On running above code multiple times, we occasionally see unexpected
		 * results. For example:
		 * 
		 * Thread-0 - before: 0 after:2
		 * 
		 * Thread-0 - before: 2 after:3
		 * 
		 * Thread-1 - before: 1 after:2
		 * 
		 * Thread-0 - before: 3 after:4
		 * 
		 * Thread-0 - before: 5 after:6
		 * 
		 * Thread-0 - before: 6 after:7
		 * 
		 * Thread-1 - before: 4 after:5
		 * 
		 * Thread-1 - before: 7 after:8
		 * 
		 * Thread-1 - before: 8 after:9
		 * 
		 * Thread-1 - before: 9 after:10
		 * 
		 * There are two problems: first line of output is incrementing the
		 * counter from 0 to 2. Second problem is, across multiple call of
		 * performTask() method, the counter increment should be gradual.
		 * 
		 * Both problems are caused by the fact that the two threads are
		 * occasionally interleaving each other. The interleaving threads are
		 * said to be interfering each other. The interfering threads' behavior
		 * is also termed as race condition.
		 * 
		 * Race conditions happen when the processes or threads depend on some
		 * shared state. Operations upon shared states are critical sections
		 * that must be mutually exclusive. Failure to obey this rule opens up
		 * the possibility of corrupting the shared state.
		 */
	}

	private void performTask() {
		int temp = counter;
		counter++;
		System.out.println(Thread.currentThread().getName() + " - before: "
				+ temp + " after:" + counter);
	}

	private class Task implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				performTask();
			}
		}
	}

	/****
	 * Using synchronized in methods
	 * 
	 * To avoid thread interference, Java provides a very easy solution for us,
	 * i.e. using the keyword synchronized in the method signature. Using this
	 * keyword requires each thread to get the 'Intrinsic Locks' before
	 * executing the method. This basically forces multiple threads to access
	 * the method one by one instead of executing it at the same time:
	 */
	@Override
	public void implementionTwo(String[] args) throws Exception {
		LogicBigThreadSynchronization demo = new LogicBigThreadSynchronization();
		SynchronizedTask task1 = demo.new SynchronizedTask();
		Thread thread1 = new Thread(task1);

		SynchronizedTask task2 = demo.new SynchronizedTask();
		Thread thread2 = new Thread(task2);

		thread1.start();
		thread2.start();
		/*
		 * Output:
		 * 
		 * Thread-0 - before: 0 after:1
		 * 
		 * Thread-0 - before: 1 after:2
		 * 
		 * Thread-0 - before: 2 after:3
		 * 
		 * Thread-1 - before: 3 after:4
		 * 
		 * Thread-1 - before: 4 after:5
		 * 
		 * Thread-1 - before: 5 after:6
		 * 
		 * Thread-1 - before: 6 after:7
		 * 
		 * Thread-1 - before: 7 after:8
		 * 
		 * Thread-0 - before: 8 after:9
		 * 
		 * Thread-0 - before: 9 after:10
		 * 
		 * In next tutorial we will look into the concept of 'Intrinsic Locks'
		 * in details.
		 */

	}

	private synchronized void performSynchronizedTask() {
		int temp = counter;
		counter++;
		System.out.println(Thread.currentThread().getName() + " - before: "
				+ temp + " after:" + counter);
	}

	private class SynchronizedTask implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				performSynchronizedTask();
			}
		}
	}

	/****
	 * Java - Intrinsic Locks and Synchronization
	 * 
	 * An intrinsic lock (aka monitor lock) is an implicit internal entity
	 * associated with each instance of objects.
	 * 
	 * The intrinsic lock enforces exclusive access to an object's state. Here
	 * 'access to an object' refers to an instance method call.
	 * 
	 * When a synchronized method is called from a thread it needs to acquire
	 * the intrinsic lock. The lock is release when the thread is done executing
	 * the method or an exception is thrown in the method which is not
	 * handled/caught.
	 * 
	 * As long as a thread owns an intrinsic lock, no other thread can acquire
	 * the same lock. The other threads will block when they attempt to acquire
	 * the lock. The blocked threads will wait till the lock is released by the
	 * currently executing thread.
	 * 
	 * 
	 * Thread.sleep() inside the synchronized method doesn't release the lock.
	 * 
	 * Intrinsic lock are reentrant. That means once a thread has acquired the
	 * lock on a method it doesn't need to acquired the lock on calling other
	 * method of the same object. The other method doesn't need to necessarily
	 * have 'synchronized' keyword.
	 * 
	 * Constructors cannot be synchronized. Using the synchronized keyword with
	 * a constructor is a syntax error. Synchronizing constructors doesn't make
	 * sense, because only the thread that creates an object should have access
	 * to it while it is being constructed.
	 * 
	 * A static method can have synchronized keyword. In this case, the thread
	 * acquires the intrinsic lock for the Class object associated with the
	 * class rather than an instance of the class.
	 */
	@Override
	public void implementionThree(String[] args) throws Exception {
		LogicBigThreadSynchronization demo = new LogicBigThreadSynchronization();
		Thread thread1 = new Thread(() -> {
			System.out.println("thread1 before call " + LocalDateTime.now());
			demo.syncMethod("from thread1");
			System.out.println("thread1 after call " + LocalDateTime.now());
		});
		Thread thread2 = new Thread(() -> {
			System.out.println("thread2 before call " + LocalDateTime.now());
			demo.syncMethod("from thread2");
			System.out.println("thread2 after call " + LocalDateTime.now());
		});

		thread1.start();
		thread2.start();
		/*
		 * We are using Thread#sleep method inside the synchronized method to
		 * keep the lock for a long time. sleep method doesn't release the lock
		 * but Object#wait does which we will explore in upcoming tutorials.
		 * 
		 * Output:
		 * 
		 * thread1 before call 2016-03-27T18:08:30.872
		 * 
		 * thread2 before call 2016-03-27T18:08:30.872
		 * 
		 * in the sync method from thread1 2016-03-27T18:08:30.886
		 * 
		 * thread1 after call 2016-03-27T18:08:35.887
		 * 
		 * in the sync method from thread2 2016-03-27T18:08:35.887
		 * 
		 * thread2 after call 2016-03-27T18:08:40.887
		 * 
		 * Per above output thread1 call to syncMethod acquired the lock on
		 * ThreadLockDemo instance 'demo' first. This call starts executing the
		 * method almost immediately.
		 * 
		 * During this execution thread2 call to syncMethod is blocked at line:
		 * demo.syncMethod("from thread2");
		 * 
		 * The output might be different on different machines. I'm running
		 * windows 10 on a 4 core/8 logical processors machine. But either of
		 * the two threads will be blocked during the sleeping time of other.
		 */
	}

	private synchronized void syncMethod(String msg) {
		System.out.println("in the sync method " + msg + " "
				+ LocalDateTime.now());
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/****
	 * Intrinsic lock is on object, not on a method
	 * 
	 * As mentioned before, if a thread has acquired the lock, other threads
	 * will be blocked even if they are calling other 'synchronized' methods of
	 * the same object. Non-synchronized methods won't be blocked.
	 */
	@Override
	public void implementionFour(String[] args) throws Exception {
		LogicBigThreadSynchronization demo = new LogicBigThreadSynchronization();
		Thread thread1 = new Thread(() -> {
			System.out.println("thread1 before call " + LocalDateTime.now());
			demo.syncMethod1("from thread1");
			System.out.println("thread1 after call " + LocalDateTime.now());
		});
		Thread thread2 = new Thread(() -> {
			System.out.println("thread2 before call " + LocalDateTime.now());
			demo.syncMethod2("from thread2");
			System.out.println("thread2 after call " + LocalDateTime.now());
		});

		thread1.start();
		thread2.start();
		/*
		 * Output:
		 * 
		 * thread1 before call 2016-03-27T20:27:09.075
		 * 
		 * thread2 before call 2016-03-27T20:27:09.075
		 * 
		 * in the syncMethod1 from thread1 2016-03-27T20:27:09.086
		 * 
		 * in the syncMethod2 from thread2 2016-03-27T20:27:14.087
		 * 
		 * thread1 after call 2016-03-27T20:27:14.087
		 * 
		 * thread2 after call 2016-03-27T20:27:19.088
		 * 
		 * If we remove 'synchronized' from one of the methods, say syncMethod2
		 * , it won't block at all.
		 */
	}

	private synchronized void syncMethod1(String msg) {
		System.out.println("in the syncMethod1 " + msg + " "
				+ LocalDateTime.now());
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private synchronized void syncMethod2(String msg) {
		System.out.println("in the syncMethod2 " + msg + " "
				+ LocalDateTime.now());
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/****
	 * Intrinsic lock are reentrant
	 * 
	 * Intrinsic lock are acquired on a per-thread basis rather than per-method
	 * call basis. Once a thread has acquired the lock it can internally call
	 * other methods without reacquiring the lock. The Lock will only be release
	 * when the thread is done with the entry method invocation.
	 */
	@Override
	public void implementionFive(String[] args) throws Exception {
		LogicBigThreadSynchronization demo = new LogicBigThreadSynchronization();
		Thread thread1 = new Thread(() -> {
			System.out.println("thread1 before call " + LocalDateTime.now());
			demo.syncMethod11("from thread1");
			System.out.println("thread1 after call " + LocalDateTime.now());
		});
		Thread thread2 = new Thread(() -> {
			System.out.println("thread2 before call " + LocalDateTime.now());
			demo.syncMethod22("from thread2");
			System.out.println("thread2 after call " + LocalDateTime.now());
		});

		thread1.start();
		thread2.start();
		/*
		 * Output:
		 * 
		 * thread1 before call 2016-03-27T20:53:21.373
		 * 
		 * thread2 before call 2016-03-27T20:53:21.373
		 * 
		 * in the syncMethod1 from thread1 2016-03-27T20:53:21.384
		 * 
		 * in the syncMethod2 from method syncMethod1, reentered call
		 * 2016-03-27T20:53:21.384
		 * 
		 * thread1 after call 2016-03-27T20:53:24.385
		 * 
		 * in the syncMethod2 from thread2 2016-03-27T20:53:24.385
		 * 
		 * thread2 after call 2016-03-27T20:53:27.386
		 */
	}

	private synchronized void syncMethod11(String msg) {
		System.out.println("in the syncMethod11 " + msg + " "
				+ LocalDateTime.now());
		syncMethod2("from method syncMethod1, reentered call");
	}

	private synchronized void syncMethod22(String msg) {
		System.out.println("in the syncMethod22 " + msg + " "
				+ LocalDateTime.now());
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * static method synchronization
	 * 
	 * For static methods we don't need instance. The intrinsic lock is on class
	 * level in that case:
	 * 
	 * Thread Starvation
	 * 
	 * Thread starvation occurs when one or more threads are constantly blocked
	 * to access a shared resource in favor of other threads.
	 * 
	 * Thread starvation can occur as a result of assigning inappropriate
	 * priorities to different threads or it could be the result of wrong
	 * synchronized block logic.
	 */

	@Override
	public void implementionSix(String[] args) throws Exception {
		Thread thread1 = new Thread(() -> {
			System.out.println("thread1 before call " + LocalDateTime.now());
			syncMethod33("from thread1");
			System.out.println("thread1 after call " + LocalDateTime.now());
		});
		Thread thread2 = new Thread(() -> {
			System.out.println("thread2 before call " + LocalDateTime.now());
			syncMethod33("from thread2");
			System.out.println("thread2 after call " + LocalDateTime.now());
		});

		thread1.start();
		thread2.start();
		/*-
		 
		thread1 before call 2016-03-27T20:59:32.326
		thread2 before call 2016-03-27T20:59:32.326
		in the sync method from thread1 2016-03-27T20:59:32.335
		in the sync method from thread2 2016-03-27T20:59:37.336
		thread1 after call 2016-03-27T20:59:37.336
		thread2 after call 2016-03-27T20:59:42.336

		 */

	}

	private static synchronized void syncMethod33(String msg) {
		System.out.println("in the sync method " + msg + " "
				+ LocalDateTime.now());
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/****
	 * Java - Synchronized Blocks
	 * 
	 * Other than using synchronized methods, code can be synchronized within a
	 * block surrounded by curly brackes { }.
	 * 
	 * Using synchronized(this){} we are saying that the block should be locked
	 * on 'this' instance.
	 * 
	 * Technically, there's no difference between the above two but with
	 * synchronized block we can have more control with regard to the scope of
	 * intrinsic lock.
	 * 
	 * By limiting the scope of synchronization we can gain some performance
	 * improvements too. Synchronization is a costly process in terms of
	 * performance. It's good to synchronize smaller blocks of code instead of a
	 * complete method.
	 * 
	 * We can use any object instance in the synchronized block:
	 * synchronized(anyInstance){} That means synchronized blocks have the
	 * flexibility of using other objects as locks whereas a synchronized method
	 * would lock only on the enclosing object instance. 'anyInstance' in above
	 * snippet cannot be null otherwise NullPointerException will be thrown.
	 * 
	 * In following examples we will explore the benefits of using 'synchronized
	 * blocks' over 'synchronized methods'.
	 * 
	 * Reducing scope of lock for performance improvements
	 * 
	 * Consider a commonly used lazy initialization of an object in
	 * multithreaded environment:
	 * 
	 */
	/*-
	For example:

	private synchronized void updateData () {
		//other code here
	}

	can be rewritten as

	private void updateData () {
		synchronized(this){
			//other code here
		}
	}
	 */
	@Override
	public void implementionSeven(String[] args) throws Exception {
		LogicBigThreadSynchronization obj = new LogicBigThreadSynchronization();

		Thread thread1 = new Thread(() -> {
			System.out.println("thread1 : "
					+ System.identityHashCode(obj.getList()));
		});
		Thread thread2 = new Thread(() -> {
			System.out.println("thread2 : "
					+ System.identityHashCode(obj.getList()));
		});

		thread1.start();
		thread2.start();
		/*-
		 * Output:

		Above code is not thread safe. Running multiple time could 
		occasionally give the different hashCode of the list,
		meaning it is initialized twice by interfering threads:

		thread2 : 999575674
		thread1 : 1741100778
		 * 
		 * 
		 */

	}

	private List<String> list;

	private List<String> getList() {
		if (list == null) {
			list = new ArrayList<>();
		}
		return list;
	}

	// To fix that we must synchronize the initializing code:

	private synchronized List<String> getSynchronizeList() {
		if (list == null) {
			list = new ArrayList<>();
		}
		return list;
	}

	/*
	 * This will fix the issue but for performance reason, it is not good to
	 * synchronize the getter all the time because the list ,after all, is
	 * initialized only once. Let's use synchronized block conditionally:
	 */
	private volatile List<String> list2;

	private List<String> getList2() {
		if (list2 == null) {
			synchronized (this) {
				if (list2 == null) {
					list2 = new ArrayList<>();
				}
			}
		}
		return list2;
		/*
		 * Above pattern is also known as Double-checked locking. It's used in
		 * singleton pattern as well.
		 * 
		 * The second null checking is referred as 'Double-checking' the
		 * variable. If list has already been accessed by another competing
		 * thread, it may have already been initialized. If so, return the
		 * initialized variable.
		 * 
		 * Declaring our list as volatile ensures that the read must happen
		 * after the write has taken place, and the reading thread will see the
		 * correct value of list instance.
		 */
	}

	private volatile List<String> list111;
	private volatile List<String> list222;
	private final Object lock1 = new Object();
	private final Object lock2 = new Object();

	/****
	 * Using different lock objects
	 * 
	 * With synchronized block we can use any arbitrary object as lock. Let's
	 * lazy initialize multiple objects from our previous example:
	 */
	@Override
	public void implementionEight(String[] args) throws Exception {
		LogicBigThreadSynchronization obj = new LogicBigThreadSynchronization();

		Thread thread1 = new Thread(() -> {
			System.out.println("thread1 list1 : "
					+ System.identityHashCode(obj.getList111()));
			System.out.println("thread1 list2 : "
					+ System.identityHashCode(obj.getList222()));
		});
		Thread thread2 = new Thread(() -> {
			System.out.println("thread2 list1 : "
					+ System.identityHashCode(obj.getList111()));
			System.out.println("thread2 list2 : "
					+ System.identityHashCode(obj.getList222()));
		});

		thread1.start();
		thread2.start();
		/*-
		 * Output:

		thread1 list1 : 571142194
		thread2 list1 : 571142194
		thread2 list2 : 751639043
		thread1 list2 : 751639043

		 * 
		 */

	}

	private List<String> getList111() {
		if (list111 == null) {
			synchronized (lock1) {
				if (list111 == null) {
					list111 = new ArrayList<>();
				}
			}
		}
		return list111;
	}

	private List<String> getList222() {
		if (list222 == null) {
			synchronized (lock2) {
				if (list222 == null) {
					list222 = new ArrayList<>();
				}
			}
		}
		return list222;
	}

	/****
	 * Generally speaking if we are accessing multiple shared resources in
	 * different thread, each resource access should be locked on different
	 * objects. We can use the shared resource instance as lock itself given
	 * that it's not null and there's a single resource access in the
	 * synchronized block.
	 */
	@Override
	public void implementionNine(String[] args) throws Exception {
		LogicBigThreadSynchronization obj = new LogicBigThreadSynchronization();

		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				obj.addToList1("thread1 list1 element=" + i);
				obj.addToList2("thread1 list2 element=" + i);
				obj.printLists();
			}
		});
		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				obj.addToList1("thread2 list1 element=" + i);
				obj.addToList2("thread2 list2 element 2" + i);
				obj.printLists();
			}
		});

		thread1.start();
		thread2.start();
		/*
		 * In above example we need synchronization on add and print methods.
		 * Removing synchronized block will cause
		 * java.util.ConcurrentModificationException
		 */
	}

	private List<String> list1 = new ArrayList<>();

	public void addToList1(String s) {
		synchronized (list1) {
			list1.add(s);
		}
	}

	public void addToList2(String s) {
		synchronized (list2) {
			list2.add(s);
		}
	}

	public void printLists() {
		String name = Thread.currentThread().getName();

		synchronized (list1) {
			list1.stream().forEach(l -> System.out.println(name + " : " + l));
		}
		synchronized (list2) {
			list2.stream().forEach(l -> System.out.println(name + " : " + l));
		}
	}

	/****
	 * Synchronized by String value
	 * 
	 * We can use String.intern as locks on strings values but that's not very
	 * reliable approach as discussed here.
	 * 
	 * In the following example we are using a map of locks for read/write
	 * operations on the file objects.
	 * 
	 * Generally speaking we should always use objects locks which our code
	 * maintains instead of relying on JVM managed objects.
	 */
	@Override
	public void implementionTen(String[] args) throws Exception {
		LogicBigThreadSynchronization obj = new LogicBigThreadSynchronization();
		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				String path = rootFolder.getAbsolutePath() + File.separatorChar
						+ i;
				obj.writeData(path, " thread1 data " + i);
				obj.readData(path);
			}
		});

		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				String path = rootFolder.getAbsolutePath() + File.separatorChar
						+ i;
				obj.writeData(path, " thread2 data " + i);
				obj.readData(path);
			}
		});

		thread1.start();
		thread2.start();

	}

	private Map<String, Object> locks = new HashMap<>();

	private static final File rootFolder = new File("logs");

	static {
		if (!rootFolder.exists()) {
			rootFolder.mkdir();
		}
	}

	private void writeData(String path, String data) {
		synchronized (getLock(path)) {
			try {
				Files.write(Paths.get(path), data.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void readData(String path) {
		synchronized (getLock(path)) {
			String s = null;
			try {
				s = new String(Files.readAllBytes(Paths.get(path)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(s);
		}
	}

	private Object getLock(String path) {
		if (!locks.containsKey(path)) {
			locks.put(path, new Object());
		}

		return locks.get(path);
	}

	/****
	 * Java - Deadlock
	 * 
	 * A deadlock is a situation which occurs when a thread enters a indefinite
	 * waiting state because a lock requested is being held by another thread,
	 * which in turn is waiting for another lock held by the first thread.
	 * 
	 * For example in the following code a thread attempts to move an item from
	 * one list object 'list1' to another list object 'list2' and at the same
	 * time, another thread attempts to move an item from list2 to list1:
	 */
	@Override
	public void implementionEleven(String[] args) throws Exception {
		List<Integer> list1 = new ArrayList<>(Arrays.asList(2, 4, 6, 8, 10));
		List<Integer> list2 = new ArrayList<>(Arrays.asList(1, 3, 7, 9, 11));
		/*-
		 Thread thread1 = new Thread(() -> {
		 moveListItem(list1, list2, 2);
		 });
		 Thread thread2 = new Thread(() -> {
		 moveListItem(list2, list1, 9);
		 });

		 thread1.start();
		 thread2.start();
		 */
		/*
		 * If we run above code multiple times there are good chances that we
		 * will have a deadlock soon. Deadlocks only occurs at some unfortunate
		 * timing of the two or more threads. When that happens, the program
		 * stops progressing at a particular point. It doesn't always
		 * necessarily hang or freeze. In above simple example though, we will
		 * experience a freeze when deadlock happens.
		 * 
		 * 
		 * Let's add some logging with above example code to see the states of
		 * the two threads when deadlock occurs. Also to increase the
		 * probability of the deadlock to occur, we are adding a sleep call
		 * after the first lock has been acquired by each thread:
		 */

	}

	private static void moveListItem(List<Integer> from, List<Integer> to,
			Integer item) {
		synchronized (from) {
			synchronized (to) {
				if (from.remove(item)) {
					to.add(item);
				}
			}
		}
	}

	@Override
	public void implementionTwelve(String[] args) throws Exception {
		List<Integer> list1 = new ArrayList<>(Arrays.asList(2, 4, 6, 8, 10));
		List<Integer> list2 = new ArrayList<>(Arrays.asList(1, 3, 7, 9, 11));

		Thread thread1 = new Thread(() -> {
			moveListItem2(list1, list2, 2);
		});
		Thread thread2 = new Thread(() -> {
			moveListItem2(list2, list1, 9);
		});

		thread1.start();
		thread2.start();
		/*-
		 * I'm able to have the deadlock at the first attempt. I'm using windows 10 home with a 4 core/8 logical processors machine.

		 Output:

		 Thread-0: attempting lock for list 2052470939
		 Thread-1: attempting lock for list 571142194
		 Thread-1: lock acquired for list 571142194
		 Thread-0: lock acquired for list 2052470939
		 Thread-0: attempting lock for list  571142194
		 Thread-1: attempting lock for list  2052470939
		 * 
		 */
	}

	private static void moveListItem2(List<Integer> from, List<Integer> to,
			Integer item) {
		log("attempting lock for list", from);
		synchronized (from) {
			log("lock acquired for list", from);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log("attempting lock for list ", to);
			synchronized (to) {
				log("lock acquired for list", to);
				if (from.remove(item)) {
					to.add(item);
				}
				log("moved item to list ", to);
			}
		}
	}

	private static void log(String msg, Object target) {
		System.out.println(Thread.currentThread().getName() + ": " + msg + " "
				+ System.identityHashCode(target));
	}

	private static String message;

	/****
	 * Java - Thread Communication using wait/notify
	 * 
	 * It is common in multithreaded programming to require one thread to wait
	 * for another thread to take some action.
	 * 
	 * Semantically, there's no direct thread communication possible as per
	 * underlying thread model, instead a developer can use some condition based
	 * on a shared variable to achieve the inter-thread communication.
	 * 
	 * Let's consider following example where the thread1 prints a message while
	 * thread2 populates the same message string:
	 */

	@Override
	public void implementionThirteen(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void implementionFourteen(String[] args) throws Exception {
		Thread thread1 = new Thread(() -> {
			System.out.println(message);
		});

		Thread thread2 = new Thread(() -> {
			message = "A message from thread1";
		});

		thread1.start();
		thread2.start();

	}

	/****
	 * The above thread1's while loop is known as 'Guarded Blocks', but that's
	 * not an efficient solution as this loop is just wasting CPU cycles also we
	 * cannot avoid thread interference .
	 * 
	 * Java extends it's 'intrinsic lock' model to provide a better solution,
	 * i.e. by using java.lang.Object methods wait() and notify()/notifyAll().
	 * 
	 * All these Object's method calls are redirected to native methods, that
	 * means this mechanism is inherently provided by underlying operating
	 * system.
	 * 
	 * Let's modify above example to see how that works:
	 */
	@Override
	public void implementionFifteen(String[] args) throws Exception {
		Object lock = new Object();

		Thread thread1 = new Thread(() -> {
			synchronized (lock) {
				while (message == null) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			System.out.println(message);
		});

		Thread thread2 = new Thread(() -> {
			synchronized (lock) {
				message = "A message from thread1";
				lock.notify();
			}
		});

		thread1.start();
		thread2.start();

	}

	/****
	 * Object.wait() and Object.notify()
	 * 
	 * wait() causes the current thread to wait (blocks or suspends execution)
	 * until another thread invokes the notify() method or the notifyAll()
	 * method for this object.
	 * 
	 * wait() method throws InterruptedException - If the corresponding thread
	 * calls interrupt() then the waiting state will get interrupted (just like
	 * sleep() method). The interrupted status of the current thread is cleared
	 * when this exception is thrown.
	 * 
	 * Unlike sleep method which doesn't release the 'intrinsic lock', this
	 * method releases the 'intrinsic lock'. That means other threads have
	 * opportunity to execute other synchronized blocks/methods while this
	 * thread is in waiting state, which is good for overall performance.
	 * 
	 * Also this method call will throw IllegalMonitorStateException if the
	 * current thread is not the owner of the object's monitor (the intrinsic
	 * lock). That's the reason we used synchronized block in the above example.
	 * 
	 * In above example, we can also use synchronized methods instead. Let's
	 * modify above example to consolidate synchronization and thread
	 * communication to a single object:
	 */
	@Override
	public void implementionSixteen(String[] args) throws Exception {
		SharedObject obj = new SharedObject();

		Thread thread1 = new Thread(() -> {
			obj.printMessage();
		});

		Thread thread2 = new Thread(() -> {
			obj.setMessage("A message from thread1");
		});

		thread1.start();
		thread2.start();

	}

	/****
	 * notify() vs notifyAll()
	 * 
	 * notify() wakes up a single thread that is waiting on this object's
	 * monitor (intrinsic lock). If multiple threads are waiting then only one
	 * of them will get notified or will wake up; which thread will be notified,
	 * we cannot predict, that totally depends on the scheduler.
	 * 
	 * notifyAll() will cause all threads to wake up if they are in waiting
	 * state due to wait() call.
	 * 
	 * Since notify() and notifyAll() are also called from synchronized
	 * method/blocks: waiting thread will not start executing till the thread
	 * which calls these methods releases the lock.
	 * 
	 * 
	 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
	 * @version 1.0.0
	 * @see <a
	 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
	 *
	 */
	private static class SharedObject {
		private String message;

		public synchronized void setMessage(String message) {
			this.message = message;
			notify();
		}

		public synchronized void printMessage() {
			while (message == null) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(message);

		}
	}

	/****
	 * Java - Thread Starvation and Fairness
	 * 
	 * In multithreaded application starvation is a situation when a thread is
	 * constantly ignored to gain possession of the intrinsic lock in favor of
	 * other threads. From Oracle reference docs:
	 * 
	 * Starvation describes a situation where a thread is unable to gain regular
	 * access to shared resources and is unable to make progress. This happens
	 * when shared resources are made unavailable for long periods by "greedy"
	 * threads. For example, suppose an object provides a synchronized method
	 * that often takes a long time to return. If one thread invokes this method
	 * frequently, other threads that also need frequent synchronized access to
	 * the same object will often be blocked.
	 * 
	 * 
	 * 
	 * Let's understand that with an example. We are going to create 5 threads
	 * and display progress of each thread on Swing JProgressBar:
	 */
	@Override
	public void implementionSeventeen(String[] args) throws Exception {
		JFrame frame = createFrame();
		frame.setLayout(new FlowLayout(FlowLayout.LEFT));

		for (int i = 0; i < 5; i++) {
			ProgressThread progressThread = new ProgressThread();
			frame.add(progressThread.getProgressComponent());
			progressThread.start();
		}

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		/*
		 * As seen above, it's evident that all threads are not given the equal
		 * chance to have equal CPU time. Some threads are starving for long
		 * time to acquire the shared lock.
		 */

	}

	private static Object sharedObj = new Object();

	private static JFrame createFrame() {
		JFrame frame = new JFrame("Starvation Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(300, 200));
		return frame;
	}

	private static class ProgressThread extends Thread {
		JProgressBar progressBar;

		ProgressThread() {
			progressBar = new JProgressBar();
			progressBar.setString(this.getName());
			progressBar.setStringPainted(true);
		}

		JComponent getProgressComponent() {
			return progressBar;
		}

		@Override
		public void run() {

			int c = 0;
			while (true) {
				synchronized (sharedObj) {
					if (c == 100) {
						c = 0;
					}
					progressBar.setValue(++c);
					try {
						// sleep the thread to simulate long running task
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/****
	 * Fairness
	 * 
	 * Fairness is the situation when all threads are given equal opportunity
	 * for intrinsic lock acquisition.
	 * 
	 * As we saw in above example that the long running thread has more chances
	 * to aquire the lock again consecutively.
	 * 
	 * In general we cannot predict how underlying thread scheduler (built in
	 * O.S.) chooses the next thread for the lock acquisition.
	 * 
	 * From developer perspective, the code should not hold the lock for a long
	 * time to make a thread greedy.
	 * 
	 * We can fix the above code by using wait() method which releases the lock
	 * but goes to waiting state i.e. the scheduler cannot choose it for the
	 * lock acquisition again. That way other threads are given the equal
	 * opportunity to run.
	 */
	@Override
	public void implementionEighteen(String[] args) throws Exception {
		JFrame frame = createFrameFairness();
		frame.setLayout(new FlowLayout(FlowLayout.LEFT));

		for (int i = 0; i < 5; i++) {
			ProgressThreadFairness progressThread = new ProgressThreadFairness();
			frame.add(progressThread.getProgressComponent());
			progressThread.start();
		}

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	private static Object sharedObjFairness = new Object();

	private static JFrame createFrameFairness() {
		JFrame frame = new JFrame("Fairness Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(300, 200));
		return frame;
	}

	private static class ProgressThreadFairness extends Thread {
		JProgressBar progressBar;

		ProgressThreadFairness() {
			progressBar = new JProgressBar();
			progressBar.setString(this.getName());
			progressBar.setStringPainted(true);
		}

		JComponent getProgressComponent() {
			return progressBar;
		}

		@Override
		public void run() {

			int c = 0;
			while (true) {
				synchronized (sharedObjFairness) {
					if (c == 100) {
						c = 0;
					}
					progressBar.setValue(++c);
					try {
						// simulate long running task with wait..
						// releasing the lock for long running task gives
						// fair chances to run other threads
						sharedObjFairness.wait(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/****
	 * Java - Thread Livelock
	 * 
	 * A livelock is a recursive situation where two or more threads would keep
	 * repeating a particular code logic. The intended logic is typically giving
	 * opportunity to the other threads to proceed in favor of 'this' thread.
	 * 
	 * A real-world example of livelock occurs when two people meet in a narrow
	 * corridor, and each tries to be polite by moving aside to let the other
	 * pass, but they end up swaying from side to side without making any
	 * progress because they both repeatedly move the same way at the same time.
	 * 
	 * From Oracle reference docs:
	 * 
	 * A thread often acts in response to the action of another thread. If the
	 * other thread's action is also a response to the action of another thread,
	 * then livelock may result. As with deadlock, livelocked threads are unable
	 * to make further progress. However, the threads are not blocked - they are
	 * simply too busy responding to each other to resume work.
	 * 
	 * 
	 * 
	 * For example consider a situation where two threads want to access a
	 * shared common resource via a Worker object but when they see that other
	 * Worker (invoked on another thread) is also 'active', they attempt to hand
	 * over the resource to other worker and wait for it to finish. If initially
	 * we make both workers active they will suffer from livelock.
	 * 
	 * 
	 * -----------------------------------------------------------------------
	 * Avoiding Livelock
	 * 
	 * In above example we can fix the issue by processing the common resource
	 * sequentially rather than in different threads simultaneously.
	 * 
	 * Just like deadlock, there's no general guideline to avoid livelock, but
	 * we have to be careful in scenarios where we change the state of common
	 * objects also being used by other threads, for example in above scenario.
	 * the Worker object.
	 */
	@Override
	public void implementionNineteen(String[] args) throws Exception {
		final Worker worker1 = new Worker("Worker 1 ", true);
		final Worker worker2 = new Worker("Worker 2", true);

		final CommonResource s = new CommonResource(worker1);

		new Thread(() -> {
			worker1.work(s, worker2);
		}).start();

		new Thread(() -> {
			worker2.work(s, worker1);
		}).start();
		/*-
		 * Output:

		 There will be never ending recursion of the following output:

		 Worker 1  : handing over the resource to the worker: Worker 2
		 Worker 2 : handing over the resource to the worker: Worker 1
		 Worker 1  : handing over the resource to the worker: Worker 2
		 Worker 2 : handing over the resource to the worker: Worker 1
		 Worker 1  : handing over the resource to the worker: Worker 2
		 Worker 2 : handing over the resource to the worker: Worker 1
		 ........

		 * 
		 */
	}

	/*
	 * The Common Resource Class
	 */
	public class CommonResource {
		private Worker owner;

		public CommonResource(Worker d) {
			owner = d;
		}

		public Worker getOwner() {
			return owner;
		}

		public synchronized void setOwner(Worker d) {
			owner = d;
		}
	}

	public class Worker {
		private String name;
		private boolean active;

		public Worker(String name, boolean active) {
			this.name = name;
			this.active = active;
		}

		public String getName() {
			return name;
		}

		public boolean isActive() {
			return active;
		}

		public synchronized void work(CommonResource commonResource,
				Worker otherWorker) {
			while (active) {
				// wait for the resource to become available.
				if (commonResource.getOwner() != this) {
					try {
						wait(10);
					} catch (InterruptedException e) {
						// ignore
					}
					continue;
				}

				// If other worker is also active let it do it's work first
				if (otherWorker.isActive()) {
					System.out.println(getName()
							+ " : handover the resource to the worker "
							+ otherWorker.getName());
					commonResource.setOwner(otherWorker);
					continue;
				}

				// now use the commonResource
				System.out.println(getName()
						+ ": working on the common resource");
				active = false;
				commonResource.setOwner(otherWorker);
			}
		}
	}

	@Override
	public void implementionTwenty(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}
}
