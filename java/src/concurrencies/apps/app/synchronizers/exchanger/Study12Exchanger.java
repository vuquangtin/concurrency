package app.synchronizers.exchanger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Study12Exchanger {

	private static class Task implements Runnable {
		private int width;
		private final Exchanger<Color> exchanger;
		private Color color;
		private final int speed;

		public Task(Exchanger<Color> exchanger, Color color, int speed) {
			this.exchanger = exchanger;
			this.color = color;
			this.speed = speed;
		}

		@Override
		public void run() {
			try {
				for (int c = 0; c < 4; c++) {
					// 200進んだら色を交換
					for (; width < (c + 1) * 200; width += speed) {
						TimeUnit.MILLISECONDS.sleep(50);
					}
					width = (c + 1) * 200;
					color = exchanger.exchange(this.color);
				}
			} catch (InterruptedException ex) {
				Logger.getLogger(Study12Exchanger.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

	}

	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("Exchanger");
		MyPanel panel = new MyPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

		Exchanger<Color> exchanger = new Exchanger<>();
		ExecutorService es = Executors.newFixedThreadPool(2);
		Task t1 = new Task(exchanger, Color.RED, 3);
		Task t2 = new Task(exchanger, Color.BLUE, 5);
		panel.tasks.add(t1);
		panel.tasks.add(t2);
		es.submit(t1);
		es.submit(t2);

		/*
		 * 描画の更新用。 別スレッドから高速にrepaintを呼んでいる実装は変だが、
		 * GUI操作を本題のTaskの方に実装したくないため、このようにしてある。
		 */
		new Thread(() -> {
			while (true) {
				frame.repaint();
				try {
					Thread.sleep(10L);
				} catch (InterruptedException ex) {
					Logger.getLogger(Study12Exchanger.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}

		}).start();

	}

	private static class MyPanel extends JComponent {
		List<Task> tasks = new ArrayList<>();

		public MyPanel() throws HeadlessException {
			setPreferredSize(new Dimension(1024, 600));
		}

		@Override
		public void paint(Graphics g) {

			int y = 0;
			for (Task dt : tasks) {
				g.setColor(dt.color);
				g.fillRect(0, y, dt.width, 50);
				y += 60;
			}
		}
	}
}
