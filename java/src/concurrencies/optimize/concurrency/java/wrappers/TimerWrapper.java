package concurrency.java.wrappers;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TimerWrapper {

	public static ArrayList<TimerWrapper> timers = new ArrayList<>();

	/**
	 * The java timer to handle the one second count down.
	 */
	public Timer timer;

	public IFunction function;

	/**
	 * The unqiue id of the timer.
	 */
	public String timerID;

	/**
	 * The max time of the timer.
	 */
	private int maxTime;
	/**
	 * The remaining time on the timer.
	 */
	public int timeRemaining;

	/**
	 * Checks whether or not the timer resets itself after it runs out of time.
	 */
	public boolean doesLoop;

	/**
	 * Checks whether or not the timer is counting down.
	 */
	public boolean isPaused;

	private int interval;

	public TimerWrapper instance;

	/**
	 * Creates a timer wrapper that handles basic timer functionality.
	 * 
	 * @param TimerID
	 * @param maxTime
	 * @param doesFunctionLoop
	 */
	public TimerWrapper(String TimerID, int maxTime, IFunction function,
			boolean doesFunctionLoop, boolean isPaused, int interval) {

		this.timer = new Timer();
		this.timerID = TimerID;

		this.timeRemaining = maxTime;
		this.maxTime = maxTime;

		this.function = function;

		this.doesLoop = doesFunctionLoop;
		this.isPaused = isPaused;
		this.interval = interval;
		this.instance = this;

		setSchedule(this, interval);

	}

	/**
	 * Sets the functions to run when the timer's internal currentTimeRemaining
	 * value hits 0;
	 * 
	 * @param tWrapper
	 */
	private void setSchedule(final TimerWrapper tWrapper, int interval) {
		tWrapper.timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (isPaused == false) {
					tWrapper.tickTimerOneSecond();
					if (tWrapper.timeRemaining == 0) {
						final TimerWrapper t = tWrapper;
						Executors.callable(new Runnable() {
							@Override
							public void run() {
								function.execute();
							}
						});

					}
				}
			}
		}, interval);
	}

	/**
	 * Resets the timer's current time to it's max time value;
	 */
	public void resetTime() {
		this.timeRemaining = this.maxTime;
	}

	/**
	 * Tick's the timer's internal currentTimeRemaining value by one and handles
	 * logic on whether or not to reset it.
	 */
	public void tickTimerOneSecond() {

		// Log.v("Timer Tick","Tick timer one interval");
		if (this.timeRemaining <= 0 && this.doesLoop) {
			this.restart();
		}
		if (this.timeRemaining <= 0 && !this.doesLoop) {
			return;
		}
		this.timeRemaining--;
		final TimerWrapper t = this;

		Executors.callable(new Runnable() {
			@Override
			public void run() {
				t.function.countDownExecute();
			}
		});

		// Log.v("Timer Tick Finish","Time remaining: "
		// +Integer.toString(this.timeRemaining));

		if (this.doesLoop == true && this.timeRemaining == 0) {
			setSchedule(this, this.interval);
			return;
		} else if (this.timeRemaining != 0) {
			setSchedule(this, this.interval);
			return;
		} else {
			return;
		}
	}

	/**
	 * Sets the maximum amount of time on the timer.
	 * 
	 * @param seconds
	 */
	public void setMaxTime(int seconds) {
		this.maxTime = seconds;
	}

	/**
	 * Sets the amount of time remaining on this timer.
	 * 
	 * @param seconds
	 */
	public void setTimeRemaining(int seconds) {
		this.timeRemaining = seconds;
	}

	/**
	 * Sets the timer's loop functionality.
	 * 
	 * @param doesLoop
	 */
	public void setDoesLoop(boolean doesLoop) {
		this.doesLoop = doesLoop;
	}

	/**
	 * Adds time to the timer's internal currentTimeRemaining value. Ignore's
	 * it's maxTime value;
	 * 
	 * @param seconds
	 */
	public void addTime(int seconds) {
		int Seconds = Math.abs(seconds);
		this.timeRemaining += Seconds;
	}

	/**
	 * Subtracts time from a timer's internal currentTimeRemaining value.
	 * 
	 * @param seconds
	 */
	public void subtractTime(int seconds) {
		int Seconds = Math.abs(seconds);
		this.timeRemaining -= Seconds;
	}

	/**
	 * Returns true if the timer has no time remaining and it doesn't loop.
	 * 
	 * @return
	 */
	public boolean isFinished() {
		if (this.timeRemaining == 0 && this.doesLoop == false)
			return true;
		else
			return false;
	}

	/**
	 * Set's the timer's isPaused value to false so that it can resume counting
	 * down.
	 */
	public void resume() {
		this.isPaused = false;
	}

	/**
	 * Sets the current timer isPaused=false and resets it's current time value
	 * so that it can start counting.
	 */
	public void start() {
		this.isPaused = false;
		this.resetTime();
	}

	/**
	 * Pauses the current timer so that it can't count down anymore.
	 */
	public void pause() {
		this.isPaused = true;
	}

	/**
	 * Restarts the timer by setting isPaused to false and resetting the time
	 * value to the max value.
	 */
	public void restart() {
		this.start();
	}

	/**
	 * Stops the timer and prevents it from counting down any more. The timer
	 * can be reinitialized with restart();
	 */
	public void stop() {
		this.pause();
		this.timeRemaining = 0;
	}

	/**
	 *
	 * @param timerID
	 * @param maxTime
	 * @param function
	 * @param doesLoop
	 *            Checks whether or
	 * @param isPaused
	 *            If false the timer will start counting down immediately. If
	 *            true, then it will need to be manually started.
	 */
	public static void createTimer(String timerID, int maxTime,
			IFunction function, boolean doesLoop, boolean isPaused, int interval) {
		if (timers == null)
			timers = new ArrayList<>();
		TimerWrapper t = new TimerWrapper(timerID, maxTime, function, doesLoop,
				isPaused, interval);
		timers.add(t);
	}

	/**
	 * Get a timer from the list of timers that are created.
	 * 
	 * @param timerID
	 * @return
	 */
	public static TimerWrapper getTimer(String timerID) {
		if (timers == null)
			timers = new ArrayList<>();
		for (TimerWrapper t : timers) {
			if (t.timerID.equals(timerID)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Stops all timers held by the timers list and set's their times to 0 and
	 * isPaused values to true.;
	 */
	public static void StopAllTimers() {
		for (TimerWrapper t : timers) {
			t.stop();
		}
	}

	/**
	 * Starts all timers held by the timers list. Sets their currentTime values
	 * to their maxValue and sets isPaused to false;
	 */
	public static void StartAllTimers() {
		for (TimerWrapper t : timers) {
			t.start();
		}
	}

	/**
	 * Pauses all timers held by the timers list. Sets their isPaused values to
	 * true but does not affect their time values.
	 */
	public static void PauseAllTimers() {
		for (TimerWrapper t : timers) {
			t.pause();
		}
	}

	/**
	 * Resumes all timers held by the timers list. Sets their isPaused values to
	 * false but does not affect their time value.
	 */
	public static void ResumeAllTimers() {
		for (TimerWrapper t : timers) {
			t.resume();
		}
	}

	/**
	 * Restarts all of the timers held by the timers list. Sets their
	 * currentTime values to their max values and sets isPaused to false.
	 */
	public static void RestartAllTimers() {
		for (TimerWrapper t : timers) {
			t.restart();
		}
	}
}