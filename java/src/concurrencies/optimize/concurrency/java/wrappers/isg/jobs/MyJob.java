package concurrency.java.wrappers.isg.jobs;

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
public class MyJob implements Runnable,Comparable<Object>{
	
	protected JobListener jobListener;
	protected long timeOfCreation;
	protected String name;
	private static int msgNumber=1;

	public MyJob(String myName, JobListener myJobListener)
	{
		timeOfCreation = System.currentTimeMillis();
		name = myName;
		jobListener =myJobListener;
	}
	


	@Override
	public void run() {
		
	
			
			System.out.println("sending msg....msgNumber : "+msgNumber);
			msgNumber++;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			finally{
				jobListener.jobDone(Thread.currentThread());
				
			}
		
		
		
	}

	@Override
	public int compareTo(Object obj) {
		MyJob thatJob = (MyJob) obj;
		
		if(timeOfCreation>thatJob.timeOfCreation)
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