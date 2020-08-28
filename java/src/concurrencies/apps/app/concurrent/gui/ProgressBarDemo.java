package app.concurrent.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */


public class ProgressBarDemo extends JPanel implements ActionListener,
		PropertyChangeListener {

	private JProgressBar progressBar;
	private JButton startButton;
	private JTextArea taskOutput;
	private Task task;

	class Task extends SwingWorker<Void, Void> {
		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {
			Random random = new Random();
			int progress = 0;
			// Initialize progress property.
			setProgress(0);
			while (progress < 100) {
				if (progress < 50) {
					process1();
					try {
						Thread.sleep(random.nextInt(1000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					taskOutput.append("Calculating...Recency  value.."
							+ task.getProgress() + "\n");
					progress += random.nextInt(10);
					setProgress(Math.min(progress, 100));
				} else {
					process2();
					try {
						Thread.sleep(random.nextInt(1000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					taskOutput.append("Calculating...Frequency  value.."
							+ task.getProgress() + "\n");
					progress += random.nextInt(10);
					setProgress(Math.min(progress, 100));
				}

			}
			return null;
		}

		public void process1() {
			int k = 0;
			for (int i = 0; i < 1000; i++) {
				for (int j = 0; j < 1000; j++) {
					k = i + j;
				}
			}
			taskOutput.append("Finished...Recency  value.." + k + "\n");
		}

		public void process2() {
			int k = 0;
			for (int i = 0; i < 1000; i++) {
				for (int j = 0; j < 1000; j++) {
					k = i + j;
				}
			}
			taskOutput.append("Finished...Frequency  value.." + k + "\n");
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			startButton.setEnabled(true);
			setCursor(null); // turn off the wait cursor
			taskOutput.append("Done!\n");
		}
	}

	public ProgressBarDemo() {
		super(new BorderLayout());

		// Create the demo's UI.
		startButton = new JButton("Start");
		startButton.setActionCommand("start");
		startButton.addActionListener(this);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setForeground(Color.cyan);
		progressBar.setStringPainted(true);
		progressBar.setUI(new MyProgressUI());

		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(5, 5, 5, 5));
		taskOutput.setEditable(false);

		JPanel panel = new JPanel();
		panel.add(startButton);
		panel.add(progressBar);

		add(panel, BorderLayout.PAGE_START);
		add(new JScrollPane(taskOutput), BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	}

	/**
	 * Invoked when the user presses the start button.
	 */
	public void actionPerformed(ActionEvent evt) {
		startButton.setEnabled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		// Instances of javax.swing.SwingWorker are not reusuable, so
		// we create new instances as needed.
		task = new Task();
		task.addPropertyChangeListener(this);
		task.execute();
	}

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

	/**
	 * Invoked when task's progress property changes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			// taskOutput.append(String.format("Completed %d%% of task.\n",
			// task.getProgress()));
		}
	}

	/**
	 * Create the GUI and show it. As with all GUI code, this must run on the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("ProgressBarDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new ProgressBarDemo();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}