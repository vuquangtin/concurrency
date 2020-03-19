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
public interface ITaskManager {
	
	public boolean insertReoccurenceTask(int id, int priority, int time);
	public boolean insertTask(int id, int priority);
	public int getNextTask();
	
}