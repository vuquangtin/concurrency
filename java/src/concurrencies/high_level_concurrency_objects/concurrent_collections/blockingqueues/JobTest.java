package blockingqueues;

import java.util.Random;

public class JobTest {

	private static JobManager jobManager;

	public static void main(String[] args) {
		jobManager = new JobManager(5, 3);
		jobManager.start();
		generateJobs(20);
		jobManager.close();
	}

	public static void after() throws InterruptedException {
		Thread.sleep(30000);

	}

	public static void generateJobs(int count) {
		for (int i = 0; i < count; i++) {
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Job job = new Job(String.valueOf("Job" + new Random().nextInt()));
			jobManager.addJob(job);
		}
	}
}