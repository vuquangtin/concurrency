package app.concurrent.gui;

import java.awt.BorderLayout;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class DateTimeExecutor extends JFrame {

	Date d = new Date();
	JLabel timeLabel = new JLabel();

	public DateTimeExecutor() {
		this.setTitle("Date & Time Frame Application");
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		timeLabel.setText(d.toString());
		this.add(timeLabel, BorderLayout.CENTER);
		ScheduledExecutorService scheduledExecutorService = Executors
				.newScheduledThreadPool(1);

		Runnable task = () -> {
			d = new Date();
			timeLabel.setText(d.toString());
		};
		scheduledExecutorService.scheduleAtFixedRate(task, 0, 1,
				TimeUnit.SECONDS);

	}

	public static void main(String[] args) {
		DateTimeExecutor app = new DateTimeExecutor();
		app.setBounds(50, 50, 600, 400);
		app.setVisible(true);

	}
}