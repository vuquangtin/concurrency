package executors.schedulers;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class TimerMultiTaskTest {
	static long startTime;

	public static void main(String[] args) {
		TimerTask timerTask1 = new TimerTask() {
			@Override
			public void run() {
				System.out.println("begin timer task 1："
						+ (System.currentTimeMillis() - startTime));
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		TimerTask timerTask2 = new TimerTask() {
			@Override
			public void run() {
				System.out.println("begin timer task 2:"
						+ (System.currentTimeMillis() - startTime));
			}
		};

		Timer timer = new Timer();
		startTime = System.currentTimeMillis();
		timer.schedule(timerTask1, 1000);
		timer.schedule(timerTask2, 3000);
	}
}