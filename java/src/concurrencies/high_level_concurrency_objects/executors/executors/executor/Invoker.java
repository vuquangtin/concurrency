package executors.executor;

import java.util.concurrent.Executor;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class Invoker implements Executor {
	@Override
	public void execute(Runnable r) {
		r.run();
	}

	public void execute() {
		Executor executor = new Invoker();
		executor.execute(() -> {
			// task to be performed
		});
	}
}