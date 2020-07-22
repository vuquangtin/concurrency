package core.java.multi.threading;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import core.java.base.TemplateMethod;

/**
 * https://www.logicbig.com/tutorials/core-java-tutorial/java-multi-threading.
 * html
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class LogicBigThreadBasics extends TemplateMethod {

	public static void main(String[] args) {
		new LogicBigThreadBasics().runTemplateMethod(args);

	}

	private static class MyThread extends Thread {
		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread().getName() + ": " + i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// https://www.logicbig.com/tutorials/core-java-tutorial/java-multi-threading/java-creating-thread.html
	@Override
	public void implementionOne(String[] args) throws Exception {
		MyThread thread = new MyThread();
		thread.start();

		MyThread thread2 = new MyThread();
		thread2.start();

		System.out.println(Thread.currentThread().getName());

	}

	private static class MyThreadRunner implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread().getName() + ": " + i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void implementionTwo(String[] args) throws Exception {
		MyThreadRunner threadRunnable = new MyThreadRunner();
		Thread thread = new Thread(threadRunnable);
		thread.start();

		MyThreadRunner threadRunnable2 = new MyThreadRunner();
		Thread thread2 = new Thread(threadRunnable2);
		thread2.start();

		System.out.println(Thread.currentThread().getName());

	}

	private static class MyThreadPriority extends Thread {
		private int c;

		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();

			System.out.println(threadName + " started.");
			for (int i = 0; i < 1000; i++) {
				c++;
				try {
					TimeUnit.MICROSECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(threadName + " ended.");
		}
	}

	// https://www.logicbig.com/tutorials/core-java-tutorial/java-multi-threading/java-thread-priority.html
	@Override
	public void implementionThree(String[] args) throws Exception {
		MyThreadPriority thread = new MyThreadPriority();
		thread.setName("thread 1");
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();

		MyThreadPriority thread2 = new MyThreadPriority();
		thread2.setName("thread 2");
		thread2.setPriority(Thread.MAX_PRIORITY);
		thread2.start();

	}

	private static class Task implements Runnable {
		private int c;

		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();

			System.out.println(threadName + " started.");
			for (int i = 0; i < 1000; i++) {
				c++;
			}

			System.out.println(threadName + " ended.");
		}
	}

	/****
	 * Threads without Yield
	 */
	@Override
	public void implementionFour(String[] args) throws Exception {
		Task task1 = new Task();
		new Thread(task1).start();

		Task task2 = new Task();
		new Thread(task2).start();

	}

	private static class TaskWithYield implements Runnable {
		private final boolean shouldYield;
		private int c;

		public TaskWithYield(boolean shouldYield) {
			this.shouldYield = shouldYield;
		}

		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();

			System.out.println(threadName + " started.");
			for (int i = 0; i < 1000; i++) {
				c++;
				if (shouldYield) {
					Thread.yield();
				}
			}

			System.out.println(threadName + " ended.");
		}
	}

	/****
	 * Threads with Yield
	 * 
	 * Let's modify our above code to use yield method for one of the thread:
	 * 
	 * ....
	 * 
	 * 
	 * In above example we are yielding the first thread in favor of other. As a
	 * result the first thread spends less CPU time and it becomes slower than
	 * the other, hence 'Thread-0 ended.' printed at the end all the time
	 * 
	 * Output
	 * 
	 * Thread-0 started.
	 * 
	 * Thread-1 started.
	 * 
	 * Thread-1 ended.
	 * 
	 * Thread-0 ended.
	 * 
	 * Just like thread priorities, yield method behavior varies from OS to OS.
	 * We shouldn't make coding decisions based on this method. Also this is
	 * only a hint, the thread scheduler (on OS level) is free to ignore this
	 * hint.
	 * 
	 */
	@Override
	public void implementionFive(String[] args) throws Exception {
		TaskWithYield task1 = new TaskWithYield(true);
		new Thread(task1).start();

		TaskWithYield task2 = new TaskWithYield(false);
		new Thread(task2).start();

	}

	/***
	 * Java - Daemon Threads
	 * 
	 * Difference between Daemon and Non Daemon(User Threads) is that: JVM waits
	 * for non-daemon threads to finish executing before terminate the main
	 * program. On the other hand, JVM doesn't wait for daemon thread to finish.
	 * JVM can exist even if daemon threads are still running.
	 * 
	 * An example for a daemon thread is the garbage collection thread.
	 * 
	 * By default a new thread is a daemon thread, if the thread which is
	 * creating it, is also daemon. The 'main' thread (which invokes the Java
	 * main method) is a non-daemon thread, hence all threads created from it
	 * are also non-daemon by default. We can make a thread daemon or non-daemon
	 * by using method Thread#setDaemon(true/false)
	 * 
	 * Calling System.exit(0); will terminate the JVM regardless of, running
	 * threads are daemon or not.
	 * 
	 */
	@Override
	public void implementionSix(String[] args) throws Exception {
		MemoryWatcherThread.start();
		for (int i = 0; i < 100000; i++) {
			String str = new String("str" + i);
			list.add(str);
		}
		System.out.println("end of main method");

	}

	/*
	 * Output:
	 * 
	 * Memory used :6 MB
	 * 
	 * Memory used :7 MB
	 * 
	 * Memory used :8 MB
	 * 
	 * Memory used :10 MB
	 * 
	 * Memory used :11 MB
	 * 
	 * Memory used :12 MB
	 * 
	 * Memory used :14 MB
	 * 
	 * Memory used :15 MB
	 * 
	 * Memory used :16 MB
	 * 
	 * Memory used :17 MB
	 * 
	 * Memory used :19 MB
	 * 
	 * Memory used :20 MB
	 * 
	 * Memory used :21 MB
	 * 
	 * end of main method
	 * 
	 * Memory used :23 MB
	 * 
	 * Process finished with exit code -1
	 */
	static List<String> list = new ArrayList<>();

	public static class MemoryWatcherThread implements Runnable {
		public static void start() {
			Thread thread = new Thread(new MemoryWatcherThread());
			thread.setPriority(Thread.MAX_PRIORITY);
			thread.setDaemon(true);
			thread.start();
		}

		@Override
		public void run() {

			long memoryUsed = getMemoryUsed();
			System.out.println("Memory used :" + memoryUsed + " MB");

			while (true) {
				long memoryUsed1 = getMemoryUsed();
				if (memoryUsed != memoryUsed1) {
					memoryUsed = memoryUsed1;
					System.out.println("Memory used :" + memoryUsed + " MB");
				}
			}
		}

		private long getMemoryUsed() {
			return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
					.freeMemory()) / 1024 / 1024;
		}
	}

	/****
	 * Java - Thread Joining
	 * 
	 * Thread#join() halts the calling thread execution until the thread
	 * represented by this instance terminates.
	 * 
	 * That means aThreadInstance.join() will block the thread in which this
	 * method is called until 'aThreadInstance' fishes executing.
	 * 
	 * Following simple example demonstrates the concept.
	 */

	@Override
	public void implementionSeven(String[] args) throws Exception {
		JoiningTask task1 = new JoiningTask();
		Thread thread1 = new Thread(task1);
		thread1.start();
		thread1.join();// here the main thread will wait until thread1 fishes.
		System.out.println("after join");

		JoiningTask task2 = new JoiningTask();
		new Thread(task2).start();
		/*
		 * Output:
		 * 
		 * Thread-0 started.
		 * 
		 * Thread-0 ended.
		 * 
		 * after join
		 * 
		 * Thread-1 started.
		 * 
		 * Thread-1 ended.
		 * 
		 * Note Thread-1 starts only after Thread-0 terminates.
		 */

	}

	private static class JoiningTask implements Runnable {

		@Override
		public void run() {
			int c = 0;
			String threadName = Thread.currentThread().getName();

			System.out.println(threadName + " started.");
			for (int i = 0; i < 1000; i++) {
				c += i;
			}

			System.out.println(threadName + " ended.");
		}
	}

	/****
	 * Calculating Factorial of multiple integers in parallel
	 * 
	 * This is more meaningful example of using join method.
	 * 
	 * We spawn a single thread A from the main thread which in turn creates n
	 * number of threads to calculate factorial of n integers. The threads A
	 * waits for all calculator threads to finish before printing the final
	 * results.
	 */
	@Override
	public void implementionEight(String[] args) throws Exception {
		final List<Integer> integers = Arrays.asList(10, 12, 13, 14, 15, 20);

		new Thread(new Runnable() {// thread A
					@Override
					public void run() {
						List<FactorialCalculator> threads = new ArrayList<>();
						for (Integer integer : integers) {
							FactorialCalculator calc = new FactorialCalculator(
									integer);
							threads.add(calc);
							calc.start();
						}
						for (FactorialCalculator calc : threads) {
							try {
								calc.join();
								System.out.println(calc.getNumber() + "! = "
										+ calc.getFactorial());

							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();
		/*
		 * Output:
		 * 
		 * 10! = 3628800
		 * 
		 * 12! = 479001600
		 * 
		 * 13! = 6227020800
		 * 
		 * 14! = 87178291200
		 * 
		 * 15! = 1307674368000
		 * 
		 * 20! = 2432902008176640000
		 */

	}

	/****
	 * Other overloaded join methods
	 * 
	 * void join(long millis): Waits at most millis milliseconds for this thread
	 * to finish. A timeout of 0 means to wait forever.
	 * 
	 * void join(long millis, int nanos) : Waits at most millis milliseconds
	 * plus nanos nanoseconds for this thread to finish.
	 * 
	 * 
	 * 
	 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
	 * @version 1.0.0
	 * @see <a
	 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
	 *
	 */
	private static class FactorialCalculator extends Thread {

		private final int number;
		private BigDecimal factorial;

		FactorialCalculator(int number) {
			this.number = number;
		}

		@Override
		public void run() {
			factorial = calculateFactorial(number);
		}

		private static BigDecimal calculateFactorial(int number) {
			BigDecimal factorial = BigDecimal.ONE;
			for (int i = 1; i <= number; i++) {
				factorial = factorial.multiply(new BigDecimal(i));
			}
			return factorial;
		}

		public BigDecimal getFactorial() {
			return factorial;
		}

		public int getNumber() {
			return number;
		}
	}

	/****
	 * Java - Thread Interrupts An interrupt is the indication to a thread that
	 * it should stop what it is doing and do something else.
	 * 
	 * Interrupts are operating system level mechanism to signal threads about
	 * some particular event.
	 * 
	 * In Java Thread#interrupt() method is used to interrupt a thread
	 * 
	 * Within the interrupted thread, Thread.currentThread().isInterrupted() can
	 * be used to find interrupted state. OR if the thread is blocked on
	 * Object.wait(..), Thread.join(...) or Thread.sleep(..),
	 * InterruptedException will be thrown. That means all method calls which
	 * throws InterruptedException will receive this exception when this thread
	 * is interrupted.
	 * 
	 * Following examples show how to use this feature.
	 */
	@Override
	public void implementionNine(String[] args) throws Exception {
		InterruptTask task1 = new InterruptTask();
		Thread thread1 = new Thread(task1);
		thread1.start();
		while (true) {
			if (Math.random() > 0.5) {
				thread1.interrupt();
				break;
			}
			TimeUnit.MILLISECONDS.sleep(1);
		}
		/*
		 * Output:
		 * 
		 * Running above example multiple times can result in two outputs:
		 * 
		 * task running .. 1
		 * 
		 * interrupted exception
		 * 
		 * Terminating task
		 * 
		 * or
		 * 
		 * task running .. 1
		 * 
		 * task running .. 2
		 * 
		 * task running .. 3
		 * 
		 * interrupted flag=true
		 * 
		 * Terminating task
		 * 
		 * Since I tested the above on a machine having multiple cores, the main
		 * thread and the thread doing the task can truly run in parallel,
		 * that's the reason we should always use the method
		 * Thread.isInterrupted besides catching the InterruptedException.
		 */
	}

	private static class InterruptTask implements Runnable {

		@Override
		public void run() {
			int c = 0;

			while (true) {

				System.out.println("task running .. " + ++c);
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("interrupted flag=true");
					terminate();
					return;
				}
				try {
					TimeUnit.MICROSECONDS.sleep(1);
				} catch (InterruptedException e) {
					System.out.println("interrupted exception ");
					terminate();
					return;
				}
			}

		}

		private void terminate() {
			System.out.println("Terminating task");
		}
	}

	/****
	 * Swing animation interrupt example
	 * 
	 * Following Swing example demonstrates how to use interrupts to make an
	 * alternate decision.
	 */
	@Override
	public void implementionTen(String[] args) throws Exception {
		JFrame frame = new JFrame("Interrupt Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(500, 500));
		frame.add(createAnimationContainer());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	private static Component createAnimationContainer() {
		JPanel panel = new JPanel(new BorderLayout());
		AnimatedPanel animatedPanel = new AnimatedPanel();
		panel.add(animatedPanel);
		JButton button = new JButton("Reverse");
		panel.add(button, BorderLayout.SOUTH);
		button.addActionListener(e -> {
			animatedPanel.getThread().interrupt();

		});
		return panel;
	}

	private static class AnimatedPanel extends JComponent {
		private int angle = 0;
		boolean clockwise = true;
		private Thread thread;

		AnimatedPanel() {
			startAnimation();
		}

		private void startAnimation() {
			thread = new Thread(() -> {
				while (true) {
					angle++;
					if (angle >= 360) {
						angle = 0;
					}
					if (thread.isInterrupted()) {
						clockwise = !clockwise;
					}
					// no need to create EDT
					repaint();
					try {
						TimeUnit.MILLISECONDS.sleep(10);
					} catch (InterruptedException e) {
						clockwise = !clockwise;

					}
				}
			});
			thread.start();
		}

		@Override
		public void paint(Graphics g) {
			g.setColor(Color.MAGENTA);
			g.fillArc(10, 10, 400, 400, clockwise ? -angle : angle, 30);
		}

		public Thread getThread() {
			return thread;
		}
	}
}
