package executors.customthreadpoolexecutor;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.channels.FileChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class WorkerThread extends Thread {
	private ThreadPoolState poolState;
	private final BlockingQueue<Runnable> unitTask;
	private final BlockingQueue<Runnable> waitingTaskQueue;
	private final FileChannel fileChannel;
	private final String fileName;
	private static AtomicBoolean isFileOperationInProcess = new AtomicBoolean(false);
	private final int PREFILL_STARTING_LIMIT;
	private final int PREFILL_ENDING_LIMIT;

	WorkerThread(BlockingQueue<Runnable> taskQueue, ThreadPoolState poolState, FileChannel fileChannel,
			String fileName) {
		this.unitTask = new ArrayBlockingQueue<>(1);
		this.waitingTaskQueue = taskQueue;
		this.poolState = poolState;
		this.poolState.incrementThreadCount();
		this.fileChannel = fileChannel;
		this.fileName = fileName;
		this.PREFILL_STARTING_LIMIT = waitingTaskQueue.remainingCapacity() * 4 / 5;
		this.PREFILL_ENDING_LIMIT = waitingTaskQueue.remainingCapacity() * 1 / 5;
	}

	@Override
	public void run() {
		Runnable task;
		while (!poolState.isShutDown()) {
			try {
				task = unitTask.poll();
				if (task != null) {
					task.run();
				} else {
					tryFillingQueueFromFile();
					task = waitingTaskQueue.poll(poolState.getThreadTimeoutInMillis(), TimeUnit.MILLISECONDS);
					if (task != null) {
						task.run();
					} else {
						if (poolState.getActiveThreadCount().decrementAndGet() >= poolState.getCorePoolSize()) {
							// terminating thread
							return;
						} else {
							poolState.getActiveThreadCount().incrementAndGet();
						}
					}
				}
			} catch (InterruptedException shutdownInterrupt) {
				// log
			}
		}
		if (poolState.isShutDownNow()) {
			// terminating thread
			poolState.decrementThreadCount();
		} else {
			// complete tasks in queue
			while (!waitingTaskQueue.isEmpty()) {
				task = waitingTaskQueue.poll();
				if (task != null) {
					task.run();
				}
			}
			// terminating thread
			poolState.decrementThreadCount();
		}
	}

	private void tryFillingQueueFromFile() {
		System.out.println(Thread.currentThread().getName() + "::" + waitingTaskQueue.size() + ":"
				+ poolState.getTasksInFileStorage() + ":" + isFileOperationInProcess.get() + ":"
				+ PREFILL_STARTING_LIMIT + ":" + PREFILL_ENDING_LIMIT);
		if (poolState.getTasksInFileStorage().get() > 0
				&& waitingTaskQueue.remainingCapacity() >= PREFILL_STARTING_LIMIT) {
			if (isFileOperationInProcess.compareAndSet(false, true)) {
				// to make read and write synchronous
				synchronized (fileChannel) {
					try (FileInputStream fis = new FileInputStream(fileName);
							ObjectInputStream ois = new ObjectInputStream(fis)) {
						while (poolState.getTasksInFileStorage().getAndDecrement() > 0
								&& waitingTaskQueue.remainingCapacity() >= PREFILL_ENDING_LIMIT) {
							Runnable task = (Runnable) ois.readObject();
							waitingTaskQueue.add(task);
						}
					} catch (Exception e) {
						// File IO Exception
						e.printStackTrace();
					}
				}
				isFileOperationInProcess.set(false);
			}
		}
	}

	public boolean submit(Runnable task) {
		return unitTask.offer(task);
	}
}