package app.concurrent.gui;


import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExecutorTimer {
	public static void main(String[] args) {
		/*
		 * ScheduledExecutorService scheduledExecutorService =
		 * Executors.newScheduledThreadPool(2);
		 * scheduledExecutorService.scheduleAtFixedRate(() -> {
		 * System.out.println("updating..."); }, 1000, 1000,
		 * TimeUnit.MILLISECONDS);
		 */

		new JlabelUpdater(new JLabel(), 5, 1000).startTimer();

	}

}

class JlabelUpdater {
	private JLabel label;
	private Integer timerTickCount;
	private Integer tickerIntervalInMillis;
	private ScheduledExecutorService scheduledExecutorService = Executors
			.newScheduledThreadPool(1);

	public JlabelUpdater(JLabel label, Integer timerTickCount,
			Integer tickerIntervalInMillis) {
		this.label = label;
		this.timerTickCount = timerTickCount;
		this.tickerIntervalInMillis = tickerIntervalInMillis;
	}

	public void startTimer() {
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			if (timerTickCount == 0) {
				scheduledExecutorService.shutdown();
			}

			System.out.println("timer running: " + timerTickCount);
			changeText(timerTickCount + "");
			timerTickCount--;
		}, 0, tickerIntervalInMillis, TimeUnit.MILLISECONDS);

	}

	private void changeText(final String text) {
		EventQueue.invokeLater(() -> {
			label.setText(text);
			System.out.println("text = " + text);
		});
	}
}