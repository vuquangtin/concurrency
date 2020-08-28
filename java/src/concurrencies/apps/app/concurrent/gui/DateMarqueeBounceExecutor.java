package app.concurrent.gui;

import java.awt.BorderLayout;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class DateMarqueeBounceExecutor extends JFrame {

	// show Date
	Date d = new Date();
	JLabel timeLabel = new JLabel();
	JPanel panel = new JPanel();

	// show MarqueString
	JLabel javaWorld = new JLabel();
	int x = -100;
	int y = 130;
	int width = 100;
	int height = 100;

	// show BouncingBall
	JLabel ball = new JLabel();
	boolean endX = false;
	boolean endY = false;
	int xBall = 0;
	int yBall = 0;
	int widthBall = 57;
	int heightBall = 57;

	public DateMarqueeBounceExecutor() {

		// show date
		this.setLayout(new BorderLayout());
		this.setTitle("Multi Application");
		this.add(panel, BorderLayout.CENTER);

		// timeLabel.setHorizontalAlignment(JLabel.CENTER);
		timeLabel.setText(d.toString());
		timeLabel.setBounds(200, 50, 200, 200);
		panel.setLayout(null);
		panel.add(timeLabel);

		// show Marquee
		String str = "Java World";
		javaWorld.setText(str);
		javaWorld.setBounds(x, y, width, height);
		panel.add(javaWorld);

		// show bouncingBall
		ImageIcon img = new ImageIcon(
				"C:\\Users\\salma\\Documents\\NetBeansProjects\\BouncingBall\\src\\bouncingball\\football2.gif");
		ball.setIcon(img);
		panel.add(ball);
		ball.setBounds(xBall, yBall, widthBall, heightBall);

		ScheduledExecutorService scheduledExecutorService = Executors
				.newScheduledThreadPool(3);
		Runnable showDateTask = () -> {
			d = new Date();
			timeLabel.setText(d.toString());

		};
		Runnable showMarqueeTask = () -> {
			x++;
			javaWorld.setBounds(x, y, width, height);
			if (x >= this.getWidth()) {
				x = -100;
			}
		};
		Runnable showBallTask = () -> {
			if (endX == false) {
				xBall++;
				if (xBall >= (panel.getWidth() - ball.getWidth())) {
					endX = true;
				}
			} else if (endX == true) {
				xBall--;
				if (xBall <= 0) {
					endX = false;
				}
			}
			if (endY == false) {
				yBall++;
				if (yBall >= panel.getHeight() - ball.getHeight()) {
					endY = true;
				}
			} else if (endY == true) {
				yBall--;
				if (yBall <= 0) {
					endY = false;
				}
			}
			ball.setBounds(xBall, yBall, widthBall, heightBall);

		};

		scheduledExecutorService.scheduleAtFixedRate(showDateTask, 0, 10,
				TimeUnit.MILLISECONDS);
		scheduledExecutorService.scheduleAtFixedRate(showMarqueeTask, 0, 10,
				TimeUnit.MILLISECONDS);
		scheduledExecutorService.scheduleAtFixedRate(showBallTask, 0, 10,
				TimeUnit.MILLISECONDS);
	}

	public static void main(String[] args) {
		DateMarqueeBounceExecutor app = new DateMarqueeBounceExecutor();
		app.setBounds(50, 50, 600, 400);
		app.setVisible(true);
	}

}