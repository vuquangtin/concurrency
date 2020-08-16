package concurrency.java.optimize.executor;

import java.util.LinkedList;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TaskQueue {

    private boolean accept = true;
    private final LinkedList<Runnable> tasks = new LinkedList<>();

    public synchronized boolean enqueue(Runnable task) {
        if (accept) {
            return tasks.offer(task);
        }
        return false;
    }

    public synchronized Runnable dequeue() {
        return tasks.poll();
    }

    public synchronized boolean isEmpty() {
        return tasks.isEmpty();
    }

    public synchronized void rejectNew() {
        accept = false;
    }
}