package running.threads.in.scheduling2;

public interface ScheduledExceptionHandler {
	/**
	 * @return true if the task should be rescheduled; false otherwise
	 */
	boolean exceptionOccurred(Throwable e);
}
