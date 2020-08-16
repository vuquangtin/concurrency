package concurrency.java.optimize.executor;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public final class KeySequentialRunner<Key> {

    private final class KeyRunner {

        private boolean notTriggered = true;
        private final TaskQueue tasks = new TaskQueue();
        private final Key key;

        KeyRunner(Key key) {
            this.key = key;
        }

        void enqueue(Runnable task) {
            if (!tasks.enqueue(task)) {
                throw new RejectedExecutionException(rejection());
            }
        }

        synchronized void triggerRun() {
            if (notTriggered) {
                try {
                    run(tasks.dequeue());
                    notTriggered = false;
                } catch (RejectedExecutionException e) {
                    synchronized (keyRunners) {
                        if (tasks.isEmpty()) {
                            keyRunners.remove(key);
                        }
                    }
                    throw new RejectedExecutionException(rejection(), e);
                }
            }
        }

        private void run(Runnable task) {
            underlyingExecutor.execute(() -> {
                runSafely(task);
                Runnable next = tasks.dequeue();
                if (next == null) {
                    synchronized (keyRunners) {
                        next = tasks.dequeue();
                        if (next == null) {
                            keyRunners.remove(key);
                        }
                    }
                }
                if (next != null) {
                    try {
                        run(next);
                    } catch (RejectedExecutionException e) {
                        // complete the task and the remaining ones on this thread when the execution is rejected
                        tasks.rejectNew();
                        do {
                            runSafely(next);
                        } while ((next = tasks.dequeue()) != null);
                        synchronized (keyRunners) {
                            keyRunners.remove(key);
                        }
                    }
                }
            });
        }

        private void runSafely(Runnable task) {
            try {
                task.run();
            } catch (Throwable t) {
                exceptionHandler.onException(key, t);
            }
        }

        private String rejection() {
            return "task for the key '" + key + "' rejected";
        }
    }

    private final Executor underlyingExecutor;
    private final TaskExceptionHandler<Key> exceptionHandler;
    private final HashMap<Key, KeyRunner> keyRunners = new HashMap<>();

    public KeySequentialRunner(Executor underlyingExecutor) {
        this.underlyingExecutor = underlyingExecutor;
        this.exceptionHandler = new TaskExceptionHandler<Key>() {
        };
    }

    public KeySequentialRunner(Executor underlyingExecutor, TaskExceptionHandler<Key> exceptionHandler) {
        this.underlyingExecutor = underlyingExecutor;
        this.exceptionHandler = exceptionHandler;
    }

    public void run(Key key, Runnable task) {
        Util.checkNotNull(task);
        KeyRunner runner;
        synchronized (keyRunners) {
            runner = keyRunners.get(key);
            if (runner == null) {
                runner = new KeyRunner(key);
                keyRunners.put(key, runner);
            }
            runner.enqueue(task);
        }
        runner.triggerRun();
    }
}