package concurrency.java.optimize.tasks;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TaskFactory {

    public static Task getTask(JobTypeConstants jobType) {
        Task task = null;
        switch (jobType) {
            case DEMO_ONE:
                task = new DemoOneTask();
                break;
            case DEMO_TWO:
                task = new DemoTwoTask();
                break;
            default:
                break;

        }
        return task;
    }
}