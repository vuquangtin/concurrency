package app.concurrent.gui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
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
public class LongTaskInEDT extends JFrame {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LongTaskInEDT taskInAWTThread = new LongTaskInEDT();
				taskInAWTThread.setVisible(true);
			}
		});
	}

	public LongTaskInEDT() throws HeadlessException {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JButton killHim = new JButton();

		killHim.setText("Zawies okno na 10 sek");

		killHim.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized (this) {
					try {
						this.wait(10000);
					} catch (InterruptedException exc) {
						Thread.interrupted();
					}
				}
			}
		});

		add(killHim, BorderLayout.NORTH);

		add(new JButton("Kliknij mnie!"));

		setSize(300, 200);
	}
}
