package app.concurrent.gui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class SwingWorkerCopyFiles extends JFrame {

	class CopySwingWorker extends SwingWorker<Void, Void> {

		File from;
		File to;

		public CopySwingWorker(File from, File to) {
			super();
			this.from = from;
			this.to = to;
			progressBar.setVisible(true);

			// PropertyChangeListener pozwala na wyswietlenie postepu kopiowania
			// plików
			addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					System.out.println(getProgress());
					progressBar.setValue(getProgress());
				}
			});
		}

		// Metoda uruchamiana w tle, w trakcie jej wykonania ramka jest
		// responsywna.
		@Override
		protected Void doInBackground() throws Exception {
			long size = from.length();

			// Tworzymy buforowany strumien do odczytu
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					new FileInputStream(from));
			// Tworzymy buforowany strumien do zapisu
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					new FileOutputStream(to));

			int read = bufferedInputStream.read();
			long readBytes = 0;

			try {
				while (read != -1) { // read() zwroci -1 jesli plik sie skonczyl
					bufferedOutputStream.write(read);
					read = bufferedInputStream.read();
					readBytes++;
					if (readBytes % 100000 == 0) { // Odswiezamy wynik co ok
													// 0.1mb inaczej byloby
													// nieefektywnie
						// Pozwala na ustawienie progress baru który podaje
						// postęp od 0 do 100
						int progress = (int) ((readBytes * 100.0) / size);
						setProgress(progress);

					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				bufferedOutputStream.close();
				bufferedInputStream.close();
			}

			return null;
		}

		// Metoda wykonana po koncu kopiowania
		@Override
		protected void done() {
			System.out.printf("Koniec kopiowania!");
		}
	}

	class OnCopyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int result = chooser.showOpenDialog(null);
			if (result != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File from = chooser.getSelectedFile();
			result = chooser.showSaveDialog(null);
			if (result != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File to = chooser.getSelectedFile();
			CopySwingWorker copySwingWorker = new CopySwingWorker(from, to);
			copySwingWorker.execute();

		}
	}

	public static void main(String[] args) {
		SwingWorkerCopyFiles frame = new SwingWorkerCopyFiles();
		frame.setVisible(true);
	}

	JFileChooser chooser = new JFileChooser();

	JProgressBar progressBar = new JProgressBar(0, 100);

	public SwingWorkerCopyFiles() throws HeadlessException {
		super();

		JToolBar toolBar = new JToolBar();
		JButton copy = new JButton("Copy");
		toolBar.add(copy);

		copy.addActionListener(new OnCopyActionListener());
		copy.setText("Kopiuj");

		add(toolBar, BorderLayout.NORTH);

		setSize(640, 480);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		add(progressBar, BorderLayout.SOUTH);
	}
}
