package concurrency.java.optimize;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import concurrency.java.optimize.tasks.JobTypeConstants;
import concurrency.java.optimize.tasks.Task;
import concurrency.java.optimize.tasks.TaskFactory;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExecutorManagerTest {
    private static ExecutorManager executorManager = ExecutorManager.getInstance();
    public static void main(String[] args) {
        ExecutorService executorService = executorManager.initTheadPool("BdssMessage");
        Task demoOneTask = TaskFactory.getTask(JobTypeConstants.DEMO_ONE);
        Task demoTwoTask = TaskFactory.getTask(JobTypeConstants.DEMO_TWO);
        Future<Object> f1 = executorService.submit(demoOneTask);
        Future<Object> f2 = executorService.submit(demoTwoTask);

        try {
            Object o1 = f1.get();
            Object o2 = f2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorManager.closeExecutorService(executorService);
        }
    }
}


