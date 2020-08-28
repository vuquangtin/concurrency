package app.concurrent.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import concurrencies.utilities.Resource;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class BallExcutor extends JFrame {

	ImageIcon image = new ImageIcon(Resource.PATH + ("ball.png"));
	JLabel label = new JLabel(image);
	Button pause = new Button("Pause");
	Button resume = new Button("Resume");

	int x, y = 0;
	boolean xFlag, yFlag = true;
	volatile static boolean flag = true;

	BallExcutor() {
		setTitle("Bouncing Ball");

		setVisible(true);
		setBounds(400, 150, 600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		label.setBounds(200, 200, 100, 100);
		pause.setBounds(150, 310, 80, 30);
		resume.setBounds(300, 310, 80, 30);
		pause.setBackground(Color.RED);
		resume.setBackground(Color.GREEN);

		this.add(resume);
		this.add(pause);
		this.add(label);
		setLayout(null);
		pause.addActionListener((e) -> {
			flag = false;
		});
		resume.addActionListener((e) -> {
			flag = true;

		});

		Runnable BallTask = () -> {
			if (flag) {
				if ((label.getBounds().x + label.getBounds().width) >= this
						.getWidth() - 13) {
					xFlag = false;
				}
				if (label.getBounds().x <= 0) {
					xFlag = true;
				}
				if ((label.getBounds().y + label.getBounds().height) >= this
						.getHeight() - 30) {
					yFlag = false;
				}
				if (label.getBounds().y <= 0) {
					yFlag = true;
				}
				label.setBounds(xFlag ? ++x : --x, yFlag ? ++y : --y, 100, 100);

				try {
					Thread.sleep(10);
				} catch (InterruptedException ex) {
					Logger.getLogger(BallExcutor.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			} else {
			}

		};
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);

		schedule.scheduleAtFixedRate(BallTask, 0, 10, TimeUnit.MILLISECONDS);

	}

	public static void main(String[] args) {

		BallExcutor ball = new BallExcutor();

	}

}
