/**
 * 
 */
package com.handson.concurrency.util.concurrent.locks;

import static java.lang.System.out;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sveera
 *
 */
public class ConcurrencyExample01a {

	/*
	 * Concept of Reentrant is Thread already acquired lock, trying to lock it
	 * again.This concept is explained using ReentrantLock. In ReenterentLock you
	 * need to unlock the lock as same no.of times we locked on it.
	 */
	private static final int REENTERENT_LOCK_COUNT = 5;

	public static void main(String[] args) throws InterruptedException {
		Lock lock = new ReentrantLock(true);
		new Thread(constructCallableWithLock(lock)).start();
		// Below thread sleep is to allow the newly created thread to start and try
		// acquiring the lock.
		Thread.sleep(500);
		// In this example main thread lock is not performed until all unlocks are done
		// in child thread.
		lock.lock();
		System.out.println("Locked in main thread @" + new Date());
		lock.unlock();

	}

	private static Runnable constructCallableWithLock(Lock lock) {
		/*
		 * In this runnable implementation we have executed unlock method in try block,
		 * usually it should be finally block.To avoid nested try/catch in finally block
		 * we have done in try block for this case.
		 */
		return () -> {
			try {
				for (int i = 1; i <= REENTERENT_LOCK_COUNT; i++)
					reenterenceLockMethod(lock, i);
				for (int i = 1; i <= REENTERENT_LOCK_COUNT; i++)
					reenterenceUnLockMethod(lock, i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {

			}
		};
	}

	private static void reenterenceLockMethod(Lock lock, int i) throws InterruptedException {
		lock.lock();
		out.println("After acquiring lock in child thread @ lock counter " + i + " @ " + new Date());
		Thread.sleep(1000);
	}

	private static void reenterenceUnLockMethod(Lock lock, int i) throws InterruptedException {
		lock.unlock();
		out.println("After acquiring lock in child thread @ lock counter " + i + " @ " + new Date());
		Thread.sleep(1000);
	}

}
