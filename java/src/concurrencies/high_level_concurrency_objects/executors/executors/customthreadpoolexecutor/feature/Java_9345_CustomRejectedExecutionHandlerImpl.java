package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class Java_9345_CustomRejectedExecutionHandlerImpl implements RejectedExecutionHandler {
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		final String rejectConstant = " is rejected";
		out.println(r.toString() + rejectConstant);
	}
}
