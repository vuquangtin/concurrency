package concurrency.java.optimize.executor;

import java.util.concurrent.Executor;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public final class KeySequentialExecutor implements Executor {

    private final KeySequentialRunner<Runnable> runner;

    public KeySequentialExecutor(Executor underlyingExecutor) {
        runner = new KeySequentialRunner<>(underlyingExecutor);
    }

    public KeySequentialExecutor(Executor underlyingExecutor, TaskExceptionHandler<Runnable> exceptionHandler) {
        runner = new KeySequentialRunner<>(underlyingExecutor, exceptionHandler);
    }

    @Override
    public void execute(Runnable task) {
        runner.run(task, task);
    }
}