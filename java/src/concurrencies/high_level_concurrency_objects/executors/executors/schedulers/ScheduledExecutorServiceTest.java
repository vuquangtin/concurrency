package executors.schedulers;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class ScheduledExecutorServiceTest {
	public static void main(String[] args) {
		final TimerTask timerTask1 = new TimerTask() {
			@Override
			public void run() {
				throw new RuntimeException();
			}
		};

		final TimerTask timerTask2 = new TimerTask() {
			@Override
			public void run() {
				System.out.println("task 2");
			}
		};

		final TimerTask timerTask3 = new TimerTask() {
			@Override
			public void run() {
				System.out.println("task 3");
			}
		};

		final TimerTask timerTask4 = new TimerTask() {
			@Override
			public void run() {
				System.out.println("task 4");
			}
		};

		final TimerTask timerTask5 = new TimerTask() {
			@Override
			public void run() {
				System.out.println("task 5");
			}
		};

		final TimerTask timerTask6 = new TimerTask() {
			@Override
			public void run() {
				System.out.println("task 6");
			}
		};

		final TimerTask timerTask7 = new TimerTask() {
			@Override
			public void run() {
				System.out.println("task 7");
			}
		};

		ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
		pool.schedule(timerTask1, 1000, TimeUnit.MILLISECONDS);
		// pool.schedule(timerTask2, 2000, TimeUnit.MILLISECONDS);
		pool.schedule(timerTask3, 1000, TimeUnit.MILLISECONDS);
		pool.schedule(timerTask4, 2000, TimeUnit.MILLISECONDS);
		pool.schedule(timerTask5, 1000, TimeUnit.MILLISECONDS);
		pool.schedule(timerTask6, 2000, TimeUnit.MILLISECONDS);
		pool.schedule(timerTask7, 1000, TimeUnit.MILLISECONDS);
		pool.scheduleAtFixedRate(timerTask2, 0, 1000, TimeUnit.MILLISECONDS);
		//pool.shutdown();
	}
}