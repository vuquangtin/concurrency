package running.threads.managers.tasks;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class GetTasks implements Runnable {
	
	TaskManager tm;

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tm.listTasks();
			System.out.println(tm.getNextTask());
		}
	}

	public GetTasks(TaskManager tm) {
		super();
		this.tm = tm;
	}
	
}