package app.concurrent.gui;

import java.awt.*;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class VirusScanner {

	public static JFrame appFrame;
	public static JLabel statusString;
	private int scanNumber = 0;
	public static final ScheduledExecutorService schedule = Executors
			.newScheduledThreadPool(5);
	private static VirusScanner app = new VirusScanner();

	public void scanDesk() {

		final Runnable scanner = new Runnable() {

			@Override
			public void run() {

				try {
					appFrame.setVisible(true);
					scanNumber++;
					Calendar cal = Calendar.getInstance();
					DateFormat df = DateFormat.getDateTimeInstance(
							DateFormat.FULL, DateFormat.MEDIUM);
					statusString.setText("scan ->" + scanNumber + "start at->"
							+ df.format(cal.getTime()));
					Thread.sleep(1000 + new Random().nextInt(10000));
					appFrame.setVisible(false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		// 重点
		final ScheduledFuture<?> scheduleManager = schedule
				.scheduleAtFixedRate(scanner, 1, 15, TimeUnit.SECONDS);
		schedule.schedule(new Runnable() {

			@Override
			public void run() {

				scheduleManager.cancel(true);
				schedule.shutdown();
				appFrame.dispose();
			}
		}, 60, TimeUnit.SECONDS);
	}

	public static void main(String[] args) {

		appFrame = new JFrame();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		appFrame.setSize(400, 700);
		appFrame.setLocation(dimension.width / 2 - appFrame.getWidth() / 2,
				dimension.height / 2 - appFrame.getWidth() / 2);
		statusString = new JLabel();
		appFrame.add(statusString);
		appFrame.setVisible(false);
		app.scanDesk();

		BeeperControl control = new BeeperControl();
		control.beepForAnHour();
	}
}

class BeeperControl {

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	public void beepForAnHour() {

		final Runnable beeper = new Runnable() {

			public void run() {

				System.out.println("beep");
			}
		};
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(
				beeper, 10, 10, TimeUnit.SECONDS);
		scheduler.schedule(new Runnable() {

			public void run() {

				beeperHandle.cancel(true);
			}
		}, 60, TimeUnit.SECONDS);
	}
}