package blockingqueues;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JobManager {

	private final BlockingQueue<Job> jobQueue;
	private final ExecutorService pool;
	private final List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
	private final List<JobExecThread> threadLists = new ArrayList<JobExecThread>();

	public JobManager(Integer queueSize, Integer numberOfThreads) {
		Objects.requireNonNull(queueSize);
		Objects.requireNonNull(numberOfThreads);

		jobQueue = new ArrayBlockingQueue<Job>(queueSize);
		pool = Executors.newFixedThreadPool(numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			final JobExecThread jobThread = new JobExecThread(jobQueue);
			threadLists.add(jobThread);
		}
	}

	public Boolean addJob(Job job) {
		Boolean b = this.jobQueue.offer(job);
		if (b) {
			System.out.println("Job " + job.getName() + " insert success.");
		} else {
			System.out.println(
					"Job insert failed. May be queue is full. remainingCapacity:" + jobQueue.remainingCapacity());
		}
		return b;
	}

	public void start() {
		threadLists.forEach(jobThread -> futures.add(pool.submit(jobThread)));
	}

	public void close() {
		threadLists.forEach(jobThread -> jobThread.stop());
		futures.forEach(future -> {
			try {
				final Integer count = future.get();
				System.out.println("Thread processed " + count + " jobs.");
			} catch (final Exception e) {
				System.out.println("Exception when getting result of Thread.");
			}
		});
		pool.shutdown();
	}
}