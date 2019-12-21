package basic.concurrency;

import java.util.concurrent.Callable;

import org.junit.Test;

public class TestSynchronizedExecution {
	
	static TestRunnable trun = new TestRunnable();
	
	static class TestRunnable implements Runnable {

		@Override
		public void run() {
			System.out.println("I am a test runnable");
			
		}
	}

	static class SimpleRunnable implements Runnable {

		@Override
		public void run() {			
			System.out.println("I am a Simple Runnable");
		}
	}

	static class SimpleCallable implements Callable<Void> {
		public Void call() throws Exception {			
			System.out.println("I am a Simple Callable");
			return null;
		}		
	}

	static class ThreadRun extends Thread {

		public void run() {
			testMethod1();
		}

		@Test
		private synchronized void testMethod1() {
			for (int i = 0; i <= 7; i++) {
				new SimpleRunnable().run();
			}
			trun.run();
		}
	}

	static class ThreadCall extends Thread {

		public void run() {
			testMethod2();
		}

		@Test
		private synchronized void testMethod2() {
			for (int i = 0; i <= 7; i++) {
				try {
					new SimpleCallable().call();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			trun.run();
		}
	}

	static class ThreadTest extends Thread {
		public void run() {
			for (int i = 0; i <= 7; i++) {
				new SimpleRunnable().run();
			}
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i <= 15; i++) {
			if ((i & 1) == 0) { 
				new ThreadRun().start();
			} else {
				new ThreadCall().start();
			}
		}

		for (int i = 0; i <= 7; i++) {
			new ThreadTest().start();
		}
	}
}