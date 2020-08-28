package app.concurrent.gui;

import java.awt.GridLayout;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
public class JButtonRunnable extends JButton implements Runnable {

	String[] tekst = { "To", "jest", "animowany", "przycisk" };
	int pauza = 1000;
	boolean czynny = true;

	JButtonRunnable() {
		super();
	}

	JButtonRunnable(String[] arg1, int arg2) {
		tekst = arg1;
		pauza = arg2;
	}

	@Override
	public void run() {

		int i = 0;

		while (czynny) {

			if (i < tekst.length - 1)
				i++;
			else
				i = 0;

			setText(tekst[i]);
			// setBackground(new Color(
			// (float)Math.random(),(float)Math.random(),(float)Math.random()
			// ));

			try {
				Thread.sleep(pauza);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame f = new JFrame();
				f.setLayout(new GridLayout(2, 1));
				f.setSize(200, 200);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JButtonRunnable b1 = new JButtonRunnable();
				f.add(b1);

				String[] innyTekst = { "inny", "tekst", "do", "animowanego",
						"przycisku" };
				// wykorzystanie drugiego konstruktora pozwalajacego zmienic
				// tekst i szybkosc:
				JButtonRunnable b2 = new JButtonRunnable(innyTekst, 100);
				f.add(b2);

				ExecutorService exec = Executors.newFixedThreadPool(2);
				// Executors.newSingleThreadExecutor();

				exec.execute(b1);
				exec.execute(b2);

				exec.shutdown();

				f.setVisible(true);
			}
		});

	}

}
