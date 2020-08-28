package app.concurrent.schedulers;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.awt.GridLayout;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

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
public class JButtonScheduled extends JButton implements Runnable {

	String[] tekst = { "To", "jest", "przycisk", "animowany", "przez",
			"ScheduledExecutorService" };
	int i = 0;

	public JButtonScheduled() {
		super();
	}

	@Override
	public void run() {
		if (i < tekst.length - 1)
			i++;
		else
			i = 0;
		setText(tekst[i]);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame f = new JFrame();
				f.setLayout(new GridLayout(3, 1));
				f.setSize(200, 200);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JButtonScheduled b1 = new JButtonScheduled();
				f.add(b1);
				JButtonScheduled b2 = new JButtonScheduled();
				f.add(b2);
				JButtonScheduled b3 = new JButtonScheduled();
				f.add(b3);
				f.setVisible(true);

				final ScheduledExecutorService scheduler = Executors
						.newScheduledThreadPool(3);
				// uwaga: liczba okreslajaca pule watkow okresla ile watkow moze
				// dzialac jednoczenie
				// , a nie maksymalna liczbe watkow w puli jak np. w przypadku
				// newFixedThreadPool(int)
				// Executors.newScheduledThreadPool(1); tez bedzie dzialac

				// zadanie powtarzane cyklicznie - czas liczony od uruchomienia
				// poprzedniego wykonania
				// takie zadanie moze byc przerwane jedynie przez anulowanie -
				// patrz nizej
				final ScheduledFuture<?> sc1 = scheduler.scheduleAtFixedRate(
						b1, 1000, 50, MILLISECONDS);

				// zadanie powtarzane - czas liczony od zakonczenia poprzedniego
				// wykonania
				scheduler.scheduleWithFixedDelay(b2, 2, 1, SECONDS);

				// jednokrotne wywolanie metody run z zadanym opoznieniem
				scheduler.schedule(b3, 5, SECONDS);

				// Anulowanie pierwszego watku po 15 sekundach
				scheduler.schedule(new Runnable() {
					@Override
					public void run() {
						sc1.cancel(true);
					}
				}, 15, SECONDS);
			}
		});

	}

}