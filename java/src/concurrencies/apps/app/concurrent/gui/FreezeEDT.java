package app.concurrent.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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

public class FreezeEDT {
	private static final int MAX = 10;
	private static final long SLEEP_TIME = 500;
	private static final String FREEZE_EDT = "Freeze EDT";
	private static final String THAWED_EDT = "Thawed EDT";
	private JPanel mainPanel = new JPanel();
	private JTextArea textarea = new JTextArea(25, 30);
	private List<JComponent> componentSetEnabledList = new ArrayList<JComponent>();

	public FreezeEDT() {
		textarea.setEditable(false);
		JButton freezeEdtBtn = new JButton(FREEZE_EDT);
		JButton thawedEdtBtn = new JButton(THAWED_EDT);

		BtnListener btnListener = new BtnListener();
		freezeEdtBtn.addActionListener(btnListener);
		thawedEdtBtn.addActionListener(btnListener);

		JPanel btnPanel = new JPanel(new GridLayout(1, 0, 5, 0));
		btnPanel.add(freezeEdtBtn);
		btnPanel.add(thawedEdtBtn);

		mainPanel.setLayout(new BorderLayout(5, 5));
		mainPanel.add(new JScrollPane(textarea), BorderLayout.CENTER);
		mainPanel.add(btnPanel, BorderLayout.SOUTH);

		componentSetEnabledList.add(freezeEdtBtn);
		componentSetEnabledList.add(thawedEdtBtn);
	}

	public JComponent getComponent() {
		return mainPanel;
	}

	private class BtnListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals(FREEZE_EDT)) {
				for (int i = 0; i < MAX; i++) {
					textarea.append(String.valueOf(i + 1) + "\n");
					try {
						Thread.sleep(SLEEP_TIME);
					} catch (InterruptedException e1) {
					}
				}
				textarea.append("Done!\n");
			} else if (command.equals(THAWED_EDT)) {
				for (JComponent jc : componentSetEnabledList) {
					jc.setEnabled(false);
				}
				new SwingWorker<Void, String>() {
					@Override
					protected Void doInBackground() throws Exception {
						for (int i = 0; i < MAX; i++) {
							publish(String.valueOf(i + 1));
							Thread.sleep(SLEEP_TIME);
						}
						return null;
					}

					@Override
					protected void process(List<String> chunks) {
						for (String chunk : chunks) {
							textarea.append(chunk + "\n");
						}
					}

					@Override
					protected void done() {
						for (JComponent jc : componentSetEnabledList) {
							jc.setEnabled(true);
						}
						textarea.append("DONE! \n");
					}

				}.execute();
			}
		}
	}

	private static void createAndShowUI() {
		JFrame frame = new JFrame("FreezeEDT");
		frame.getContentPane().add(new FreezeEDT().getComponent());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				createAndShowUI();
			}
		});
	}
}