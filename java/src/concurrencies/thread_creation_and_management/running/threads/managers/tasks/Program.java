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
public class Program {

	public static void main(String[] args) throws InterruptedException {
		TaskManager tm = new TaskManager(5);
		Thread tc;
		Thread tg;
		//while(true) {

			tc = new Thread(new CreateTasks(tm));
			tc.start();
			
			tg = new Thread(new GetTasks(tm));
			tg.start();
			tm.listTasks();
		//}

	}
}