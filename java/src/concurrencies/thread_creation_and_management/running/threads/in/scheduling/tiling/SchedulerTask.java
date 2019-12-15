package running.threads.in.scheduling.tiling;

import java.util.Calendar;
import java.util.TimerTask;

import running.threads.in.scheduling.iterators.CompositeIterator;
import running.threads.in.scheduling.iterators.RestrictedDailyIterator;

/**
 * Design Patterns
 * 
 * @see https
 *      ://github.com/tomwhite/articles/tree/master/scheduling/src/org/tiling
 *      /scheduling
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public abstract class SchedulerTask implements Runnable {

	final Object lock = new Object();

	int state = VIRGIN;
	static final int VIRGIN = 0;
	static final int SCHEDULED = 1;
	static final int CANCELLED = 2;

	TimerTask timerTask;

	protected SchedulerTask() {
		int[] weekdays = new int[] { Calendar.MONDAY, Calendar.TUESDAY,
				Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY };
		int[] weekend = new int[] { Calendar.SATURDAY, Calendar.SUNDAY };
		ScheduleIterator i = new CompositeIterator(new ScheduleIterator[] {
				new RestrictedDailyIterator(8, 0, 0, weekdays),
				new RestrictedDailyIterator(9, 0, 0, weekend) });
		System.out.println(i);
	}

	public abstract void run();

	public boolean cancel() {
		synchronized (lock) {
			if (timerTask != null) {
				timerTask.cancel();
			}
			boolean result = (state == SCHEDULED);
			state = CANCELLED;
			return result;
		}
	}

	public long scheduledExecutionTime() {
		synchronized (lock) {
			return timerTask == null ? 0 : timerTask.scheduledExecutionTime();
		}
	}

}