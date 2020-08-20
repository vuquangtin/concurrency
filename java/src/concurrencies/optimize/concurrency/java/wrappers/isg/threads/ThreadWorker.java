package concurrency.java.wrappers.isg.threads;

import java.util.PriorityQueue;

import concurrencies.utilities.Log4jUtils;
import concurrency.java.wrappers.isg.jobs.MyJob;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadWorker extends Thread {

	private int numberOfJobDoneByThread = 0;

	public boolean isStopped = false;
	private Object sharedLock;
	private PriorityQueue<MyJob> sharedJobQueue;

	public int getNumberOfJobDoneByThread() {
		return numberOfJobDoneByThread;
	}

	public ThreadWorker(String myName, MyJob job, Object mysharedLock,
			PriorityQueue<MyJob> mySharedJobQueue) {
		sharedJobQueue = mySharedJobQueue;

		Thread.currentThread().setName(myName);
		sharedLock = mysharedLock;
		if (job != null)
			echo("ThreadWorker has been created with this Job " + job.getName());

	}

	public void run() {
		MyJob r = null;

		while (!isStopped) {
			Log4jUtils.echo("thread " + getName() + " : running");

			if (!sharedJobQueue.isEmpty()) {
				if (sharedJobQueue != null) {
					r = sharedJobQueue.poll();
					if (r != null) {

						r.run();
						if (sharedJobQueue.size() < ThreadPool.limitOfJobQueue / 2) {
							synchronized (sharedLock) {
								sharedLock.notifyAll();// notify the thread pool
														// to
														// refill the jobqueue
							}
						}
					}
				} else {
					Log4jUtils.echo("error: shared job queue is null.");
				}
			} else {

				synchronized (sharedLock) {
					Log4jUtils.echo("thread " + getName()
							+ " : ThreadPool.jobQueue.size() < 0");
					Log4jUtils.echo("thread " + getName() + " : waiting");

					try {
						sharedLock.wait();
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}

			}

			numberOfJobDoneByThread++;
		}// while(true)

	}

	private void echo(String str) {
		System.out.println(str);

	}

}