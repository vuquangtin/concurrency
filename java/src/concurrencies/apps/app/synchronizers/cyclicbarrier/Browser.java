package app.synchronizers.cyclicbarrier;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * https://github.com/meo30811/workspace-jee/tree/master/DownloadManager
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Browser extends JFrame implements ActionListener {

	private int downloads;
	private JProgressBar[] balken;
	private JButton startButton;

	// Deklaration Ihrer Synchronisations-Hilfsklassen hier:

	private CyclicBarrier start;
	private CountDownLatch wellness;

	private Download download;

	public Browser(int downloads) {
		super("Mein Download-Browser");
		this.downloads = downloads;

		// Initialisierung Ihrer Synchronisations-Hilfsklassen hier:
		start = new CyclicBarrier(5);
		wellness = new CountDownLatch(5);

		// Aufbau der GUI-Elemente:
		balken = new JProgressBar[downloads];
		JPanel zeilen = new JPanel(new GridLayout(downloads, 1));

		for (int i = 0; i < downloads; i++) {
			JPanel reihe = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 10));
			balken[i] = new JProgressBar(0, 100);
			balken[i].setPreferredSize(new Dimension(500, 20));
			reihe.add(balken[i]);
			zeilen.add(reihe);

			download = new Download(balken[i], start, wellness);
			download.start();
			// neue Download-Threads erzeugen und starten
			// ggf. müssen Synchronisations-Objekte im Konstruktor übergeben
			// werden!!
			// balken ist ebenfalls zu übergeben!

		}

		startButton = new JButton("Downloads starten");
		startButton.addActionListener(this);

		this.add(zeilen, BorderLayout.CENTER);
		this.add(startButton, BorderLayout.SOUTH);

		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) throws InterruptedException {
		new Browser(5);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Blockierte Threads jetzt laufen lassen:

		startButton.setEnabled(false);
		startButton.setSelected(false);
		startButton.setText("Downloads laufen...");

		// Auf Ende aller Download-Threads warten ... erst dann die Beschriftung
		// ändern
		// Achtung, damit die Oberflaeche "reaktiv" bleibt dies in einem eigenen
		// Runnable ausfuehren!

	}
}