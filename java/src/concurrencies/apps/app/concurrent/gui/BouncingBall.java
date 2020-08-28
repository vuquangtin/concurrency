package app.concurrent.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

public class BouncingBall extends JFrame implements Runnable {

	Thread th;
	static JLabel ball = new JLabel();
	static JPanel panel = new JPanel(null);
	static JButton pause = new JButton("pause");
	static JButton resume = new JButton("resume");

	int x = 0;
	int y = 0;
	int width = 57;
	int height = 57;
	boolean endX = false;
	boolean endY = false;
	volatile boolean checkStatus = true;

	public BouncingBall() {
		this.setLayout(new BorderLayout());
		panel.setBackground(Color.white);
		this.setTitle("Bouncing Ball");
		ImageIcon img = new ImageIcon(Resource.PATH + ("football2.gif"));
		ball.setIcon(img);
		panel.setLayout(null);
		panel.add(ball);
		ball.setBounds(x, y, width, height);
		panel.add(pause);
		pause.setBounds(100, 20, 80, 40);
		resume.setBounds(400, 20, 80, 40);
		panel.add(resume);
		this.add(panel, BorderLayout.CENTER);
		th = new Thread(this);

		th.start();
		pause.addActionListener((e) -> {
			checkStatus = false;
		});
		resume.addActionListener((e) -> {
			checkStatus = true;
		});

	}

	public static void main(String[] args) {
		BouncingBall app = new BouncingBall();
		app.setBounds(50, 50, 600, 400);
		app.setVisible(true);

	}

	@Override
	public void run() {
		while (true) {
			if (checkStatus == true) {
				if (endX == false) {
					x++;
					if (x >= (panel.getWidth() - ball.getWidth()))
						endX = true;
				}

				else if (endX == true) {
					x--;
					if (x <= 0)
						endX = false;
				}
				if (endY == false) {
					y++;
					if (y >= panel.getHeight() - ball.getHeight())
						endY = true;
				}

				else if (endY == true) {
					y--;
					if (y <= 0)
						endY = false;
				}
				ball.setBounds(x, y, width, height);

				try {
					Thread.sleep(10);

				} catch (InterruptedException ex) {
					System.out.println("handle interruptedException here");
				}
			}
		}
	}
}