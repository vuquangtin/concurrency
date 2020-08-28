package app.concurrent.gui;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class OrtographyPanelMain {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				JFrame f = new JFrame("Dyktandation");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				OrtographyPanel panel = new OrtographyPanel();
				f.add(panel);

				f.setSize(panel.getPreferredSize());
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				f.setLocation(dim.width / 2 - f.getSize().width / 2, dim.height
						/ 2 - f.getSize().height / 2);

				ExecutorService exec = Executors.newSingleThreadExecutor();
				exec.execute(panel);
				exec.shutdown();

				f.setVisible(true);
			}
		});

	}
}