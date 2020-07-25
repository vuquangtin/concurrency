package executors.customthreadpoolexecutor;

import static java.lang.System.out;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class Java_9345_CustomRejectedExecutionHandlerImpl implements RejectedExecutionHandler {
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		final String rejectConstant = " is rejected";
		out.println(r.toString() + rejectConstant);
	}
}