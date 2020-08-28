package app.concurrent.gui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class NoClick extends JFrame {

	Timer timer;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				NoClick noClick = new NoClick();
				noClick.setVisible(true);
			}
		});
	}

	public NoClick() throws HeadlessException {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JButton autoPress = new JButton();

		autoPress.setText("Naciskam sie sam");

		add(autoPress);

		timer = new Timer(2000, null);

		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				autoPress.doClick();
				// timer.setDelay(timer.getDelay()-500);
			}
		});

		timer.start();

		setSize(200, 300);
	}
}