package app.concurrent.gui;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import app.concurrent.gui.animates.AnimatedButton;
import app.concurrent.gui.animates.AnimatedLabel;
import app.concurrent.gui.animates.AnimatedPanel;

/**
 * https://github.com/frncki/if.pw-java-lab/tree/master/Labkowe/Programy/Lab9
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class AnimatedPanelMain {

	static Runnable r = new Runnable() {
		int i = 0;

		@Override
		public void run() {
			System.out.println(i++);
		}
	};

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame f = new JFrame();
				f.setLayout(new GridLayout(3, 1));
				f.setSize(200, 300);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				JPanel panel = new JPanel();
				f.add(panel, BorderLayout.NORTH);

				JPanel panel1 = new JPanel();
				f.add(panel1, BorderLayout.CENTER);

				AnimatedPanel p1 = new AnimatedPanel(300);
				f.add(p1, BorderLayout.SOUTH);

				ExecutorService exec = Executors.newFixedThreadPool(3);

				AnimatedButton b1 = new AnimatedButton(100, panel);
				panel.add(b1);

				String[] otherText = { "Litwo", "Ojczyzno", "moja!",
						"Ty jestes", "jak zdrowie.", "Lorem", "ipsum", "dolor",
						"sit", "amet,", "consectetur", "adipiscing", "elit." };
				AnimatedLabel l1 = new AnimatedLabel(otherText, 200, panel1);
				panel1.add(l1);

				exec.execute(b1);
				exec.execute(l1);
				exec.execute(p1);

				exec.shutdown();

				final ScheduledExecutorService scheduler = Executors
						.newScheduledThreadPool(2);
				scheduler.scheduleAtFixedRate(r, 0, 1, SECONDS);

				scheduler.schedule(new Runnable() {
					@Override
					public void run() {
						System.out.println("Koniec programu po 20 sekundach");
						scheduler.shutdownNow();
						System.exit(0);
					}
				}, 20, SECONDS);

				f.setVisible(true);
			}
		});

	}
}