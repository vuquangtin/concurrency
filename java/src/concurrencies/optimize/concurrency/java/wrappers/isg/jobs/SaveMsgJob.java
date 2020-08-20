package concurrency.java.wrappers.isg.jobs;

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
public class SaveMsgJob extends MyJob {

	private static AtomicLong msgNumber = new AtomicLong(0);

	public SaveMsgJob(String myName, JobListener myJobListener) {
		super(myName, myJobListener);

	}

	@Override
	public void run() {
		msgNumber.incrementAndGet();

		try {

			System.out.println("sending msg....msgNumber : " + msgNumber);

			try {
				MessageSender msgSender = new MessageSender();
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
}