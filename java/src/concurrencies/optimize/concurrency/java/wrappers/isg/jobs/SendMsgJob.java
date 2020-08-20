package concurrency.java.wrappers.isg.jobs;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

import concurrency.java.wrappers.isg.threads.JobListener;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SendMsgJob<V> extends MyJob implements Callable {

	public static AtomicLong jobsProcessed = new AtomicLong(0);

	private MessageSender msgSender;

	private Object sharedLock;
	private static AtomicLong msgNumber = new AtomicLong(0);

	public SendMsgJob(String myName, JobListener myJobListener) {
		super(myName, myJobListener);

	}

	public SendMsgJob(Object mySharedLock) {
		super(null, null);
		this.sharedLock = mySharedLock;

	}

	@Override
	public void run() {
		msgNumber.incrementAndGet();
		try {

			System.out.println("sending msg....msgNumber : " + msgNumber);

			try {
				synchronized (this) {
					if (msgSender == null)
						msgSender = new MessageSender();
				}
				msgSender.sendTextToQueue();

			} catch (Exception e) {

				e.printStackTrace();
			}

		} finally {
			jobListener.jobDone(Thread.currentThread());

		}

	}

	@Override
	public int compareTo(Object obj) {
		SendMsgJob thatJob = (SendMsgJob) obj;

		if (timeOfCreation > thatJob.timeOfCreation)
			return 1;
		return 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object call() throws Exception {

		msgNumber.incrementAndGet();
		try {

			System.out.println("sending msg....msgNumber : " + msgNumber);

			try {
				synchronized (this) {
					if (msgSender == null)
						msgSender = new MessageSender();
				}
				msgSender.sendTextToQueue();

			} catch (Exception e) {

				e.printStackTrace();
			}

		} finally {
			jobListener.jobDone(Thread.currentThread());
			jobsProcessed.incrementAndGet();

		}

		return jobsProcessed;
	}
}