package app.concurrent.gui.animates;

import java.awt.Color;

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
public class AnimatedPanel extends JPanel implements Runnable {

	private int pause = 1000;
	private boolean active = true;

	public AnimatedPanel(int pauza) {
		this.pause = pauza;
	}

	@Override
	public void run() {

		while (active) {

			this.setBackground(new Color((float) Math.random(), (float) Math
					.random(), (float) Math.random()));

			try {
				Thread.sleep(pause);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}