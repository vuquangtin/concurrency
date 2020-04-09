package executors.gpcoder.threadpools;

/**
 * <h1>Tạo WorkerThread</h1> Trước khi đi vào chi tiết cách sử dụng các phương
 * thức của lớp Executors, hãy xem một task (tác vụ) sẽ được sử dụng để minh họa
 * cho các ví dụ tiếp theo
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class WorkerThread implements Runnable {

	private String task;

	public WorkerThread(String s) {
		this.task = s;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " Starting. Task = " + task);
		processCommand();
		System.out.println(Thread.currentThread().getName() + " Finished.");
	}

	private void processCommand() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Task = " + task;
	}
}
