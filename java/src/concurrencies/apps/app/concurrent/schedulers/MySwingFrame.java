package app.concurrent.schedulers;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MySwingFrame extends JFrame {

	JSlider autoSlider;
	JButton autoButton;

	public MySwingFrame() throws HeadlessException {
		// TODO Auto-generated constructor stub
		setSize(600, 100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
		autoSlider = new JSlider(0, 10, 0);
		autoButton = new JButton("Auto Clicking Button");
		add(autoSlider);
		add(autoButton);
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						System.exit(1);
					}
				});

			}

		}, 20000);
		timer.scheduleAtFixedRate(new TimerTask() {
			int i = 600;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						i = i + 10;
						if (i == 700) {
							i = 600;
						}
						MySwingFrame.this.setSize(i, 100);

					}

				});

			}
		}, 0, 1000);
		timer.scheduleAtFixedRate(new TimerTask() {
			int i = 0;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						i = i + 1;
						if (i == 10) {
							i = 0;
						}
						autoSlider.setValue(i);

					}

				});

			}

		}, 0, 500);
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						autoButton.doClick();

					}

				});

			}
		}, 0, 250);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MySwingFrame frame = new MySwingFrame();
				frame.setVisible(true);
			}
		});

	}

}