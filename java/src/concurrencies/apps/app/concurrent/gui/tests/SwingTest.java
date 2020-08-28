package app.concurrent.gui.tests;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * https://github.com/zawmyohtet2009/SampleRepo_22_01_2015/blob/888
 * a260e27fbe9bdeed56408072a566933acac7d
 * /Training/src/com/ndmm/internship/training/test/SwingTest.java
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class SwingTest extends JFrame {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SwingTest();
				// start the simulator thread now
				Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
						new Simulator(), 2, 3, TimeUnit.SECONDS);
			}
		});
	}

	public SwingTest() {
		initGui();
		setTitle("Test");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	static JTextField logField = new JTextField(20);

	public void initGui() {
		setLayout(new java.awt.FlowLayout());
		add(logField);
	}

	public static void writeLogField(final String logString) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				System.out.println("Writing: " + logString);
				logField.setText(logString);
			}
		});
	}
}

class Simulator implements Runnable {
	@Override
	public void run() {
		SwingTest.writeLogField(java.util.Calendar.getInstance().getTime()
				.toString());
	}
}
