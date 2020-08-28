package app.concurrent.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class StatusTest extends JFrame {
	private static final int NSTEPS = 1000000;

	StatusTest() {
		setTitle("Status Test");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton button = new JButton("Press Me");
		button.addActionListener(new StatusListener());

		JTextField status = new JTextField(20);
		LogHandler.setLogField(status);

		getContentPane().add(BorderLayout.NORTH, button);
		getContentPane().add(BorderLayout.SOUTH, status);

		setSize(300, 200);
		setVisible(true);
	}

	private static void TakeTime() {
		double n = 1000.0;

		for (int i = 0; i < NSTEPS; i++) {
			n = Math.pow(Math.sqrt(n + 1), 1.75);
		}
		System.out.println("Took some time");
	}

	class StatusListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			LogHandler.writeLogField("Doing something");
			TakeTime();
			LogHandler.writeLogField("Did something");
			TakeTime();
			LogHandler.writeLogField("Did something else");
			TakeTime();
			LogHandler.writeLogField("Done");
		}
	}

	public static void main(String[] args) {
		StatusTest st = new StatusTest();
	}
}