package blockingqueues;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class JobExecThread implements Callable<Integer> {

	private final BlockingQueue<Job> queue;

	private volatile boolean stop = false;

	private Integer count = 0;

	public JobExecThread(BlockingQueue<Job> jobQueue) {
		Objects.requireNonNull(jobQueue);
		this.queue = jobQueue;
	}

	@Override
	public Integer call() throws Exception {
		System.out.println("Thread start, id is: " + Thread.currentThread().getId());
		while (!stop) {
			try {
				System.out.println("Try to poll job from queue." + ", Thread id is: " + Thread.currentThread().getId());
				final Job job = queue.poll(10, TimeUnit.SECONDS);

				if (job == null) {
					continue;
				}
				count++;

				// 处理任务begin
				Thread.sleep(1000);
				// 处理任务end

				System.out
						.println("Job name is: " + job.getName() + ", Thread id is: " + Thread.currentThread().getId());

			} catch (Exception e) {
				// 处理异常
			}
		}
		return count;
	}

	public void stop() {
		System.out.println("set stop = true. " + ", Thread id is: " + Thread.currentThread().getId());
		stop = true;
	}

}
