package running.threads.in.scheduling.submits;

public interface ScheduledExceptionHandler {
	/**
	 * @return true if the task should be rescheduled; false otherwise
	 */
	boolean exceptionOccurred(Throwable e);
}
