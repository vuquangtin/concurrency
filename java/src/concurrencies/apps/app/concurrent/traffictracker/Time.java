package app.concurrent.traffictracker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Time implements Runnable {
	private boolean isRunning;
	private String timePattern = "hh:mm:ss a";
	private SimpleDateFormat timeFormat = new SimpleDateFormat(timePattern);
	Date date = new Date(System.currentTimeMillis());

	public Time() {
		this.isRunning = Thread.currentThread().isAlive();
	}

	public String getTime() {
		date = new Date(System.currentTimeMillis());
		return timeFormat.format(date);
	}

	@Override
	public void run() {
		// While running, constantly update current time
		while (isRunning) {
			TrafficTracker.timeText.setText(getTime());
		}
	}

}