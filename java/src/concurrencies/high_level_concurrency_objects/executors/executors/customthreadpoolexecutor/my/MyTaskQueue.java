package executors.customthreadpoolexecutor.my;

import java.util.ArrayList;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MyTaskQueue {
    private int _sizeLimit;
    private ArrayList<MyTask> _queue;

    public MyTaskQueue(int queueSize) {
        this._sizeLimit = queueSize;
        this._queue = new ArrayList<>(queueSize);
    }

    synchronized boolean put(MyTask task) {
        if (_queue.size() < _sizeLimit) {
            return _queue.add(task);
        }
        return false;
    }

    synchronized MyTask get() {
        if (!_queue.isEmpty()) {
            return _queue.remove(0);
        }
        return null;
    }

    synchronized boolean isEmpty() {
        return _queue.isEmpty();
    }

    synchronized boolean isFull() {
        return _queue.size() == _sizeLimit;
    }
}