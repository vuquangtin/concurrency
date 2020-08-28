package app.concurrent.gui;

import java.awt.EventQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JProgressBar;

/**
 * 
 * https://github.com/Jokniserous/staines-counter/blob/master/MainFrame.java
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class LoadingScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel imageLabel;
	private JProgressBar progressBar;
	private ScheduledExecutorService executor = Executors
			.newScheduledThreadPool(1);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoadingScreen frame = new LoadingScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame. © Bernard Borg 2019
	 */
	public LoadingScreen() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 369);
		setUndecorated(true);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new LineBorder(Color.BLUE, 10));
		contentPane.setLayout(null);

		setContentPane(contentPane);

		init();
	}

	public void init() {
		progressBar = new JProgressBar();
		progressBar.setForeground(Color.BLUE);
		progressBar.setBackground(Color.WHITE);
		progressBar.setStringPainted(true);
		progressBar.setBounds(120, 325, 175, 25);

		imageLabel = new JLabel("");
		imageLabel.setVerticalAlignment(SwingConstants.TOP);
		imageLabel.setIcon(new ImageIcon("img/stainesPic.jpg"));
		imageLabel.setBounds(10, 10, 300, 350);

		contentPane.add(progressBar);
		contentPane.add(imageLabel);

		progressBarStuff();
	}

	public void progressBarStuff() {
		Runnable myRunnable = new Runnable() {
			public void run() {
				int i = progressBar.getValue();
				i++;
				progressBar.setValue(i);

				if (progressBar.getValue() == 100) {
					executor.shutdown();
					loadMain();
				}
			}
		};

		executor.scheduleAtFixedRate(myRunnable, 0, 30, TimeUnit.MILLISECONDS);
	}

	public void loadMain() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		dispose();
	}
}