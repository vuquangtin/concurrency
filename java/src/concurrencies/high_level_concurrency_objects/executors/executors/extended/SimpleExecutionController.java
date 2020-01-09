package executors.extended;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
//@XMLElement(name = "ExecutionController")
public class SimpleExecutionController<T extends TaskGroup> implements
		ExecutionController<T>{//, Elementable {

	private final ReentrantLock globalLock = new ReentrantLock();
	private Condition finishedCondition = globalLock.newCondition();

	private T taskGroup;

	private BlockingQueue<? extends Runnable> taskQueue;
	private ThreadPoolTaskGroupHandler<T> threadPoolTaskHandler;

	//@XMLAttribute
	private boolean started;

	//@XMLAttribute
	private boolean aborted;

	//@XMLAttribute
	private int initialTaskCount;

	//@XMLAttribute
	private AtomicInteger completedTaskCount = new AtomicInteger();

	public SimpleExecutionController(T taskGroup,
			ThreadPoolTaskGroupHandler<T> threadPoolTaskHandler) {

		super();
		this.taskGroup = taskGroup;
		this.taskQueue = taskGroup.getTasks();
		this.threadPoolTaskHandler = threadPoolTaskHandler;
		initialTaskCount = taskQueue.size();
	}

	public void abort() {

		globalLock.lock();

		try {

			if (this.threadPoolTaskHandler != null) {

				if (started) {
					threadPoolTaskHandler.remove(this);
				}

				this.aborted = true;

				this.executionComplete();
			}

		} finally {

			globalLock.unlock();
		}
	}

	public void awaitExecution(long timeout) throws InterruptedException {

		globalLock.lock();

		try {

			if (finishedCondition != null) {

				finishedCondition.await(timeout, TimeUnit.MILLISECONDS);
			}

		} finally {

			globalLock.unlock();
		}

	}

	public void awaitExecution() throws InterruptedException {

		globalLock.lock();

		try {

			if (finishedCondition != null) {

				finishedCondition.await();
			}

		} finally {

			globalLock.unlock();
		}

	}

	void executionComplete() {

		globalLock.lock();

		try {

			if (finishedCondition != null) {

				finishedCondition.signalAll();

				threadPoolTaskHandler = null;
				finishedCondition = null;
			}

		} finally {

			globalLock.unlock();
		}
	}

	public int getRemainingTaskCount() {

		return taskQueue.size();
	}

	BlockingQueue<? extends Runnable> getTaskQueue() {

		return taskQueue;
	}

	public void start() {

		globalLock.lock();

		try {
			if (!started && !aborted) {

				this.threadPoolTaskHandler.add(this);
				this.started = true;
			}

		} finally {

			globalLock.unlock();
		}
	}

	public int getInitialTaskCount() {

		return initialTaskCount;
	}

	public int getCompletedTaskCount() {

		return completedTaskCount.get();
	}

	void incrementCompletedTaskCount() {

		this.completedTaskCount.incrementAndGet();
	}

	public boolean isStarted() {

		return started;
	}

	public boolean isAborted() {

		return aborted;
	}

	public boolean isFinished() {

		return started && !aborted && this.threadPoolTaskHandler == null;
	}

	public T getTaskGroup() {

		return taskGroup;
	}

//	public Element toXML(Document doc) {
//
//		return XMLGenerator.toXML(this, doc);
//	}
}
