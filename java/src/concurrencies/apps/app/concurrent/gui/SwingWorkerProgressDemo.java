package app.concurrent.gui;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
public class SwingWorkerProgressDemo extends JFrame {

	JButton button = new JButton("Start");
	JProgressBar progressBar = new JProgressBar(0, 5);
	JLabel etykieta = new JLabel();

	public SwingWorkerProgressDemo() throws HeadlessException {
		setSize(300, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		progressBar.setValue(0);
		add(button);
		add(progressBar);
		add(etykieta);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SwingWorker<String, Integer> worker2 = new SwingWorker<String, Integer>() {

					@Override
					protected void process(List<Integer> dane) {
						for (Integer progress : dane)
							progressBar.setValue(progress);
					}

					@Override
					protected String doInBackground() throws Exception {
						for (int i = 1; i <= 5; i++) {
							Thread.sleep(1000);
							publish(i);
						}
						return "Wynik";
					}

					@Override
					protected void done() {
						try {
							etykieta.setText(get());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

				};
				worker2.execute();
			}
		});

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				SwingWorkerProgressDemo f = new SwingWorkerProgressDemo();
				f.setVisible(true);
			}
		});

	}

}