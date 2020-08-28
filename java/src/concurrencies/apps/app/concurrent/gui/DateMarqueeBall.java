package app.concurrent.gui;

import java.awt.BorderLayout;
import java.util.Date;
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

public class DateMarqueeBall extends JFrame {

	Thread showDate;
	Thread showMarqueeString;

	// to show date
	static Date d = new Date();
	static JLabel timeLabel = new JLabel();

	// to show marquee String
	static JLabel javaWorld = new JLabel();
	// dimensions of marqueeString
	static int x = -100;
	static int y = 100;
	static int width = 100;
	static int height = 100;
	static int appWidth;
	static int appHeight;

	public static void main(String[] args) {
		DateMarqueeBall app = new DateMarqueeBall();
		appWidth = app.getWidth();
		appHeight = app.getHeight();
		app.setTitle("Multi Application");
		app.setBounds(50, 50, 600, 400);
		app.setVisible(true);

	}

	public static void showMsg() {
		x++;
		javaWorld.setBounds(x, y, width, height);
		if (x >= appWidth) {
			x = -100;
		}
	}

	public DateMarqueeBall() {
		this.setLayout(null);
		// to show date
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		timeLabel.setText(d.toString());
		this.add(timeLabel, BorderLayout.CENTER);
		DateTimeApp obj1 = new DateTimeApp();
		showDate = new Thread(obj1);
		showDate.start();

		// to show marquee
		String str = "Java World";
		javaWorld.setText(str);
		javaWorld.setBounds(x, y, width, height);
		this.add(javaWorld);
		MarqueeString obj2 = new MarqueeString();
		showMarqueeString = new Thread(obj2);
		showMarqueeString.start();
	}

	// inner class implements runnable to show date
	private class DateTimeApp implements Runnable {

		@Override
		public void run() {
			while (true) {
				d = new Date();
				timeLabel.setText(d.toString());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					System.out.println("handle interruptedException here");
				}

			}
		}

	}

	// inner class implements runnable to show marquee
	private class MarqueeString implements Runnable {

		@Override
		public void run() {
			while (true) {
				DateMarqueeBall.showMsg();
				try {
					Thread.sleep(10);

				} catch (InterruptedException ex) {
					System.out.println("handle interruptedException here");
				}
			}
		}
	}

}