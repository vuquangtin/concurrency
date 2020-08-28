package app.synchronizers.cyclicbarrier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

import concurrencies.utilities.Log4jUtils;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Study11CyclicBarrier {
	static Logger logger = Logger.getLogger(Study11CyclicBarrier.class
			.getName());

	private static class Task implements Runnable {
		private int width;
		private final CyclicBarrier barrier;
		private boolean moving;
		private int speed;

		public Task(CyclicBarrier barrier) {
			this.barrier = barrier;
			this.speed = new Random().nextInt(10) + 4;
		}

		@Override
		public void run() {
			try {
				for (int c = 0; c < 4; c++) {
					moving = true;
					for (; width < (c + 1) * 200; width += speed) {
						TimeUnit.MILLISECONDS.sleep(50);
					}
					width = (c + 1) * 200;
					moving = false;
					barrier.await(); // 待ち合わせ

				}
			} catch (InterruptedException | BrokenBarrierException ex) {
				logger.debug(null, ex);
			}
		}

	}

	public static void main(String[] args) throws InterruptedException {
		logger = Log4jUtils.initLog4j();
		JFrame frame = new JFrame("CyclicBarrier");
		MyPanel panel = new MyPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

		// 8スレッド
		CyclicBarrier barrier = new CyclicBarrier(8, () -> {
			// 全スレッド待ち合わせ完了時のアクション
				for (int i = 0; i < 10; i++) {
					for (Task dt : panel.tasks) {
						dt.moving = !dt.moving;
					}
					try {
						Thread.sleep(200L);
					} catch (InterruptedException e) {
					}
				}
			});
		ExecutorService es = Executors.newFixedThreadPool(8);
		for (int i = 0; i < 8; i++) {
			Task dt = new Task(barrier);
			panel.tasks.add(dt);
			es.submit(dt);

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