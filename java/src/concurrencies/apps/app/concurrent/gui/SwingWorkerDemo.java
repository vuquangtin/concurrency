package app.concurrent.gui;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class SwingWorkerDemo extends JFrame {

	JButton button = new JButton("Start");
	JProgressBar progressBar = new JProgressBar();
	JLabel etykieta = new JLabel();

	public SwingWorkerDemo() throws HeadlessException {
		setSize(300, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		add(button);
		add(progressBar);
		add(etykieta);

		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				progressBar.setIndeterminate(true);
				SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {

					@Override
					protected String doInBackground() throws Exception {
						Thread.sleep(5000);
						return "Wynik";
					}

					@Override
					protected void done() {
						try {
							etykieta.setText(get());
							progressBar.setIndeterminate(false);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

				};
				worker.execute();
			}
		});

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				SwingWorkerDemo f = new SwingWorkerDemo();
				f.setVisible(true);
			}
		});

	}

}