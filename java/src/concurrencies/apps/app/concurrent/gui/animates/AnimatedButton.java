package app.concurrent.gui.animates;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class AnimatedButton extends JButton implements Runnable {

	private String[] text = { "Can't", "touch", "this", "yeah!" };
	private int pause = 1000;
	private boolean active = true;
	private JPanel panel;

	public AnimatedButton() {
		super();
	}

	public AnimatedButton(int pauza, JPanel panel) {
		this.pause = pauza;
		this.panel = panel;
	}

	public AnimatedButton(String[] tekst, int pauza, JPanel panel) {
		this.text = tekst;
		this.pause = pauza;
		this.panel = panel;
	}

	AnimatedButton(String[] tekst, int pauza) {
		this.text = tekst;
		this.pause = pauza;
	}

	@Override
	public void run() {

		int i = 0;

		while (active) {

			if (i < text.length - 1)
				i++;
			else
				i = 0;

			setText(text[i]);

			try {
				Thread.sleep(pause);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}