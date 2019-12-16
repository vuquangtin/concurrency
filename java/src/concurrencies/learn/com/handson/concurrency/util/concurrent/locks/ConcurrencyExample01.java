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
public class ConcurrencyExample01 {

	/*
	 * Example on Simple Lock.Lock is the interface and ReenterntLock is the
	 * implementation of Lock interface.ReenterentLock supports Thread locking
	 * fairness feature as constructor flag argument. Locks also provide non
	 * blocking calls while trying to acquire the locks using tryLock().In the below
	 * example as we are using fairness flag enabled , Thread's trying to acquire
	 * lock , will be provided with lock in same order in which they are requested
	 * (fairness in locking order). Always put lock.unlock() in finally block.
	 */
	private static final int THREAD_COUNT = 10;

	public static void main(String[] args) throws InterruptedException {
		/*
		 * Observe the fairness in locking , all the locks are acquired in order in
		 * which they were requested i.e order in which locking was requested is
		 * preserved (fairness in Thread locking.Thread fairness avoids threads
		 * starvation i.e Threads waiting to acquire the lock).This fairness mechanism
		 * doesn't work with synchronized block/method
		 * 
		 */
		Lock lock = new ReentrantLock(true);
		for (int threadIndex = 1; threadIndex <= THREAD_COUNT; threadIndex++) {
			new Thread(constructCallableWithLock(lock, threadIndex)).start();
			// Below thread sleep is to allow the newly created thread to start and try
			// acquiring the lock.
			Thread.sleep(500);
		}

	}

	private static Runnable constructCallableWithLock(Lock lock, int threadIndex) {
		return () -> {
			try {
				lock.lock();
				out.println("After acquiring lock in thread - " + threadIndex + " @ " + new Date());
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		};
	}

}
