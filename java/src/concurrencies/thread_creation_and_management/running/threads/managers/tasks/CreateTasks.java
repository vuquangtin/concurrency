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
public class CreateTasks implements Runnable {
	TaskManager tm;;

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int id = (int) (Math.random() * 100);
			int priority = (int) (Math.random() * 3 + 1);
			int time = (int) (Math.random() * 2);

			System.out.println(tm.insertReoccurenceTask(id, priority, time));
		}
	}

	public CreateTasks(TaskManager tm) {
		super();
		this.tm = tm;
	}

}