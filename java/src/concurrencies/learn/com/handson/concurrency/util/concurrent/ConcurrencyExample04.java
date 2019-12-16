/**
 * 
 */
package com.handson.concurrency.util.concurrent;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample04 {

	/*
	 * Example on ScheduledExecutorService. Examples on
	 * ScheduledExecutorService.scheduleAtFixedRate and
	 * ScheduledExecutorService.scheduleWithFixedDelay. ScheduledExecutorService
	 * stops executing particular task , if there is any exception inside Runnable
	 * Task.
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread.setDefaultUncaughtExceptionHandler((Thread th, Throwable t) -> System.out.println(
				"Exception thrown in thread " + th.getName() + " and reason for exception is - " + t.getMessage()));

		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
		Runnable runnableTask1 = () -> {
			long count = 0;
			try {
				for (int i = 0; i < 10; i++) {
					count += i;
					Thread.sleep(600);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runnable Task 1 Total Count " + count + " @ Time " + new Date());
		};
		Runnable runnableTask2 = () -> {
			long count = 0;
			try {
				for (int i = 0; i < 10; i++) {
					count += i;
					Thread.sleep(600);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runnable Task 2 Total Count " + count + " @ Time " + new Date());
		};
		Runnable runnableTask3 = () -> {
			long count = 0;
			for (int i = 0; i < 20; i++) {
				count += i;
				if (count > 30)
					throw new RuntimeException("Threshold Value breached");
			}
			System.out.println("Runnable Task 3 Total Count " + count + " @ Time " + new Date());
		};

		scheduledExecutorService.scheduleAtFixedRate(runnableTask1, 10, 5000, TimeUnit.MILLISECONDS);
		scheduledExecutorService.scheduleWithFixedDelay(runnableTask2, 10, 5000, TimeUnit.MILLISECONDS);
		ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(runnableTask3, 10, 10,
				TimeUnit.MILLISECONDS);
		/*
		 * As exception is thrown in RunnableTask3, It's not executed by
		 * scheduledExecutorService after exception.Exception can be inspected using
		 * ScheduledFuture Object.
		 */
		try {
			scheduledFuture.get();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		// Code to shutdown scheduledExecutorService after 30 seconds
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Shuttingdown Scheduled Executor Service ");
				scheduledExecutorService.shutdownNow();
				System.exit(0);
			}
		}, 30000l);

	}

}
