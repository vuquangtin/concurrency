package app.concurrent.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * @se http://stackoverflow.com/a/20603012/230513
 * @see http://stackoverflow.com/a/17763395/230513
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class SwingWorkerExample {

	private final JLabel statusLabel = new JLabel("Status: ", JLabel.CENTER);
	private final JTextArea textArea = new JTextArea(20, 20);
	private JButton startButton = new JButton("Start");
	private JButton stopButton = new JButton("Stop");
	private JProgressBar bar = new JProgressBar();
	private BackgroundTask backgroundTask;
	private final ActionListener buttonActions = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent ae) {
			JButton source = (JButton) ae.getSource();
			if (source == startButton) {
				textArea.setText(null);
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				backgroundTask = new BackgroundTask();
				backgroundTask.execute();
				bar.setUI(new MyProgressUI());
				bar.setIndeterminate(true);
				bar.setForeground(Color.blue);
			} else if (source == stopButton) {
				backgroundTask.cancel(true);
				backgroundTask.done();
			}
		}
	};

	private static class MyProgressUI extends BasicProgressBarUI {

		private Rectangle r = new Rectangle();

		@Override
		protected void paintIndeterminate(Graphics g, JComponent c) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			r = getBox(r);
			g.setColor(progressBar.getForeground());
			g.fillOval(r.x, r.y, r.width, r.height);
		}
	}

	private void displayGUI() {
		JFrame frame = new JFrame("Swing Worker Example");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(5, 5));

		JScrollPane sp = new JScrollPane();
		sp.setBorder(BorderFactory.createTitledBorder("Output: "));
		sp.setViewportView(textArea);

		startButton.addActionListener(buttonActions);
		stopButton.setEnabled(false);
		stopButton.addActionListener(buttonActions);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(bar);

		panel.add(statusLabel, BorderLayout.PAGE_START);
		panel.add(sp, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.PAGE_END);

		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	private class BackgroundTask extends SwingWorker<Integer, String> {

		private int status;

		public BackgroundTask() {
			statusLabel.setText((this.getState()).toString());
		}

		@Override
		protected Integer doInBackground() throws InterruptedException {
			/*
			 * try { ProcessBuilder pb = new ProcessBuilder("ls", "-lR", "/");
			 * pb.redirectErrorStream(true); Process p = pb.start(); String s;
			 * BufferedReader stdout = new BufferedReader( new
			 * InputStreamReader(p.getInputStream())); while ((s =
			 * stdout.readLine()) != null && !isCancelled()) { publish(s); } if
			 * (!isCancelled()) { status = p.waitFor(); }
			 * p.getInputStream().close(); p.getOutputStream().close();
			 * p.getErrorStream().close(); p.destroy(); } catch (IOException ex)
			 * { ex.printStackTrace(System.err); }
			 */
			for (int i = 0; i < 1000; i++) {
				textArea.append("start " + i + "\n");
				status = 0;
			}
			Thread.sleep(1000);
			for (int i = 0; i < 1500; i++) {
				textArea.append("New " + i + "\n");
				status = 1;
			}
			status = 1;
			return status;
		}

		@Override
		protected void process(java.util.List<String> messages) {
			statusLabel.setText((this.getState()).toString());
			for (String message : messages) {
				textArea.append(message + "\n");
			}
		}

		@Override
		protected void done() {
			statusLabel.setText((this.getState()).toString() + " " + status);
			stopButton.setEnabled(false);
			startButton.setEnabled(true);
			bar.setIndeterminate(false);
		}

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SwingWorkerExample().displayGUI();
			}
		});
	}
}