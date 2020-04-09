package executors.customthreadpoolexecutor.feature;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

// Implements the Callable interface parameterized with the String class.
public class Java7_0500_ExecutableTaskRunnables implements Callable<String> {
	private static final Logger logger = Logger.getLogger("ExecutableTask");
	// It will store the name of the task.
	private String name;

	// Implement the constructor of the class to initialize the name of the task.
	public Java7_0500_ExecutableTaskRunnables(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	// Put the task to sleep for a random period of time 
	//and return a message with the name of the task.
	@Override
	public String call() throws Exception {
		try {
			long duration = (long) (Math.random() * 10);
			logger.info(this.name + ": Waiting " + duration + " seconds for results.");
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException ie) {
			logger.log(Level.SEVERE, ie.getLocalizedMessage());
			ie.printStackTrace(System.err);
		}
		return "Hello, world. I'm " + this.name;
	}
}
