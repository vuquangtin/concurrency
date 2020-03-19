package running.threads.managers.tasks;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TaskManager implements ITaskManager {

	Queue<Task> tasks;
	int tasksMaxCount;

	public TaskManager(int tasksMaxCount) {
		super();
		this.tasksMaxCount = tasksMaxCount;
		tasks = new PriorityQueue<>(tasksMaxCount, comparator);
	}

	@Override
	synchronized public boolean insertReoccurenceTask(int id, int priority,
			int time) {
		boolean result = false;
		if (tasks.size() < tasksMaxCount) {
			result = true;
			tasks.add(new Task(id, priority, time));
		} else {
			result = false;
		}
		return result;

	}

	private void addTask(int id, int priority, int time) {
		Task t = new Task(id, priority, time);
		List<Task> detectTask = tasks.stream().filter(p -> p.id == id)
				.collect(Collectors.toList());
		if (detectTask.size() == 0) {
			tasks.add(t);
			System.out.println(String.format("Insert task #:%s, p:%s, t:%s",
					id, priority, time));
		} else {
			detectTask.get(0).setId(id);
			detectTask.get(0).setPriority(priority);
			detectTask.get(0).setTime(time);
			System.out.println(String.format("Update task #:%s, p:%s, t:%s",
					id, priority, time));
		}
	}

	@Override
	public boolean insertTask(int id, int priority) {
		return insertReoccurenceTask(id, priority, 0);
	}

	@Override
	synchronized public int getNextTask() {
		if (tasks.size() > 0) {
			Task t = tasks.peek();
			int result = t.getId();
			if (t.getTime() > 0) {
				t.tmTemp = this;
				Thread runAgain = new Thread(t);
				runAgain.start();
				System.out.println(String.format(
						"Rerun task: #:%s, p:%s, t:%s", t.getId(),
						t.getPriority(), t.getTime()));

			} else {
				System.out.println(String.format(
						"Remove task: #:%s, p:%s, t:%s", t.getId(),
						t.getPriority(), t.getTime()));

			}
			tasks.remove();
			return result;
		}
		return -1;
	}

	synchronized void listTasks() {
		for (Task t : tasks) {
			System.out.println(">> " + t.toString());
		}
	}

	// Comparator anonymous class implementation
	public static Comparator<Task> comparator = new Comparator<Task>() {

		@Override
		public int compare(Task t1, Task t2) {
			int compareResult;
			compareResult = t1.getPriority() - t2.getPriority();
			if ((t1.getTime() > 0)
					&& (t2.getTime() > 0 && t1.getPriority() == t2
							.getPriority())) {
				compareResult = t1.getTime() - t2.time;
			}
			return compareResult;
		}
	};

}