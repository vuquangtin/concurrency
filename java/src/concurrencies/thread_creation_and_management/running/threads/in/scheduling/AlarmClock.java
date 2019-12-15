package running.threads.in.scheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

import running.threads.in.scheduling.iterators.DailyIterator;
import running.threads.in.scheduling.tiling.Scheduler;
import running.threads.in.scheduling.tiling.SchedulerTask;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class AlarmClock {

	private final Scheduler scheduler = new Scheduler();
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd MMM yyyy HH:mm:ss.SSS");
	private final int hourOfDay, minute, second;

	public AlarmClock(int hourOfDay, int minute, int second) {
		this.hourOfDay = hourOfDay;
		this.minute = minute;
		this.second = second;
	}

	public void start() {
		scheduler.schedule(new SchedulerTask() {
			public void run() {
				soundAlarm();
			}

			private void soundAlarm() {
				System.out.println("Wake up! " + "It's "
						+ dateFormat.format(new Date()));
				// Start a new thread to sound an alarm...
			}
		}, new DailyIterator(hourOfDay, minute, second));
	}

	public static void main(String[] args) {
		AlarmClock alarmClock = new AlarmClock(8, 37, 0);
		alarmClock.start();
	}
}
