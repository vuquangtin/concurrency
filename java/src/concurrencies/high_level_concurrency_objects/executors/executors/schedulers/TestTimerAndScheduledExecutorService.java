package executors.schedulers;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Design Patterns
 * 
 * @see <a href=
 *      'https://github.com/neooyoy/test1/tree/e109da8d34e074cc95a5fa0a7f7a3fed0bc681aa/src/main/java/com'>Link</a
 *      >
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class TestTimerAndScheduledExecutorService {

	private static int number = 0;

	private static long start;

	private Timer timer;

	public static void main(String[] args) {
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				throw new RuntimeException();
			}
		};

		TimerTask timerTask1 = new TimerTask() {
			@Override
			public void run() {
				System.out.println("执行时间"
						+ (System.currentTimeMillis() - start));

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		TimerTask timerTask2 = new TimerTask() {
			@Override
			public void run() {
				System.out.println("执行时间"
						+ (System.currentTimeMillis() - start));
			}
		};

		Timer timer = new Timer();

		try {
			timer.schedule(timerTask, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			timer.schedule(timerTask1, 1000);

			timer.schedule(timerTask2, 3000);

		}

		start = System.currentTimeMillis();
		ScheduledExecutorService scheduledExecutorService = Executors
				.newScheduledThreadPool(2);
		scheduledExecutorService.scheduleWithFixedDelay(timerTask1, 0, 1000,
				TimeUnit.MILLISECONDS);
		scheduledExecutorService.scheduleWithFixedDelay(timerTask2, 0, 1000,
				TimeUnit.MILLISECONDS);
	}
}