package concurrency.java.wrappers.isg.threads;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import concurrencies.utilities.Log4jUtils;
import concurrency.java.wrappers.isg.jobs.MyJob;

/**
 * this class uses limited queue and if any more data coming in it blocks until
 * the thread workers do the remaining tasks in the queues ,by doing this, it
 * will avoid OOM in case we use this thread pool to process the logic in a for
 * loop with millions of entries it will put 10000 jobs in the queue and wait
 * for the workers to finish half of them then it will resume and put the
 * remaining 5000 jobs in the queue
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadPool implements JobListener {

	ThreadWorker[] workers;
	private static int poolSize = 0;
	public static int limitOfJobQueue;
	public PriorityQueue<MyJob> jobQueue;
	private static boolean isPoolEmpty;
	public final CountDownLatch latch;
	private Object sharedLock = new Object();
	private static AtomicLong totalJobDoneInASecond = new AtomicLong(0);
	private ArrayList<AtomicLong> performancePerSecond = new ArrayList<AtomicLong>();

	public ThreadPool(int size, int mylimitOfJobQueue) {

		if (size > 0) {
			poolSize = size;
		} else {
			poolSize = 10;
		}
		if (mylimitOfJobQueue > 0) {
			limitOfJobQueue = mylimitOfJobQueue;
		} else {
			limitOfJobQueue = 100;
		}

		workers = new ThreadWorker[poolSize];
		latch = new CountDownLatch(poolSize);
		jobQueue = new PriorityQueue<MyJob>();
		startTheCounterThreads();
		for (int i = 0; i < poolSize; i++) {
			workers[i] = new ThreadWorker("myThreadWorker" + i, null,
					sharedLock, jobQueue);
			workers[i].start();
			Log4jUtils.echo("thread " + workers[i].getName() + " was created ");
		}

		Log4jUtils.echo("Thread pool created with this " + poolSize + " threads");
	}

	public void assignJob(MyJob job, Long jobsize) throws InterruptedException {
		for (int i = 0; i < jobsize; i++) {
			Log4jUtils.echo("job has been pushed to unAssinged jobs Stack ");

			if (jobQueue.size() >= limitOfJobQueue) {
				synchronized (sharedLock) {
					Log4jUtils.echo("Thread Pool Job Queue is full, its limit is "
							+ limitOfJobQueue + " "
							+ Thread.currentThread().getName()
							+ " thread is going to wait ");
					sharedLock.wait();
				}

			} else {
				Log4jUtils.echo("Thread Pool Job Queue is " + jobQueue.size()
						+ ", will add a job now. ");

				jobQueue.add(job);
				synchronized (sharedLock) {
					sharedLock.notifyAll();
				}
			}
		}

	}

	public Boolean isPoolEmpty() {

		return isPoolEmpty;

	}

	public static void showThreadStatus(Thread thrd) {
		System.out.println(thrd.getName() + " Alive:" + thrd.isAlive()
				+ " State:" + thrd.getState());
	}

	@Override
	public void jobDone(Thread thread) {
		totalJobDoneInASecond.getAndIncrement();
		latch.countDown();
		Log4jUtils.echo("thread " + thread.getName() + " is done with the job....");

		if (jobQueue.size() > 0) {

		} else {
			isPoolEmpty = true;
		}

	}

	public int getRemainingJobsSize() {
		return jobQueue.size();
	}

	public void reportStatistics() {
		Log4jUtils.echo("*********************Statistics****************************");
		for (int i = 0; i < poolSize; i++) {
			Log4jUtils.echo("thread " + workers[i].getName() + " has completed "
					+ workers[i].getNumberOfJobDoneByThread() + "  jobs....");
		}

		Log4jUtils.echo("\n*********************thread Pool Performance over time****************************");

		for (int j = 0; j < performancePerSecond.size(); j++)

		{
			AtomicLong performanceInSecond = performancePerSecond.get(j);
			Log4jUtils.echo(" : Time [ " + j + "th second] =  " + performanceInSecond
					+ "  jobs....");
		}

	}

	public void shutDown() {
		for (int i = 0; i < poolSize; i++) {
			workers[i].isStopped = true;
			Log4jUtils.echo("thread " + workers[i].getName() + " was stopped ");
			workers[i] = null;
		}

	}

	public void startTheCounterThreads() {
		Thread counter = new Thread(new Runnable() {
			public void run() {

				while (true) {

					try {
						performancePerSecond.add(totalJobDoneInASecond);
						totalJobDoneInASecond = new AtomicLong(0);
						Thread.sleep(1000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}

				}
			}

		});

		counter.start();
	}
}