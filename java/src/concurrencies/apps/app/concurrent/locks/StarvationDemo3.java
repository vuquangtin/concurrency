package app.concurrent.locks;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class StarvationDemo3 {

	private static Object lock = new Object();

	public static void main(String[] args) {
 
		JFrame frame = new JFrame("线程饥饿——synchronized");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout(FlowLayout.LEFT));
		frame.setSize(new Dimension(350, 200));
		for (int i = 0; i < 10; i++) {
			JProgressBar progressBar = new JProgressBar();
			progressBar.setStringPainted(true);
			progressBar.setMinimum(0);
			progressBar.setMaximum(1000);
			frame.add(progressBar);
			new Thread(() -> {
				progressBar.setString(Thread.currentThread().getName());
				int c = 0;
				while (true) {
					synchronized (lock) {
						if (c >= 1000)
							break;
						progressBar.setValue(++c);
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
						}
					}
				}
			}).start();
		}
		frame.setVisible(true);
	}

}