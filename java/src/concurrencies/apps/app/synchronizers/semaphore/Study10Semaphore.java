package app.synchronizers.semaphore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import app.synchronizers.cyclicbarrier.Study11CyclicBarrier;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Study10Semaphore {
	static Logger logger = Logger.getLogger(Study10Semaphore.class.getName());

	private static class Task implements Runnable {
		private int width;
		private final Semaphore semaphore;
		private boolean moving;

		public Task(Semaphore semaphore) {
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			try {
				for (int c = 0; c < 10; c++) {

					semaphore.acquire(); // セマフォを獲得できるのは3つまで
					moving = true;

					for (int i = 0; i < 80; i++) {
						width += 1;
						TimeUnit.MILLISECONDS.sleep(5);
					}
					moving = false;
					semaphore.release(); // 開放
					TimeUnit.MILLISECONDS.sleep(10);
				}
			} catch (InterruptedException ex) {
				logger.debug(null, ex);
			}
		}

	}

	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("Semaphore");
		MyPanel panel = new MyPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.CENTER);
		frame.pack();

		Semaphore semaphore = new Semaphore(3); // セマフォは3つまで
		ExecutorService es = Executors.newFixedThreadPool(8); // 8スレッド
		for (int i = 0; i < 8; i++) {
			Task dt = new Task(semaphore);
			es.submit(dt);
			panel.tasks.add(dt);
		}

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
					logger.debug(null, ex);
				}
			}

		}).start();

		frame.setVisible(true);
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
				g.setColor(dt.moving ? Color.RED : Color.GRAY);
				g.fillRect(0, y, dt.width, 50);
				y += 60;
			}
		}
	}
}