package app.synchronizers.countdownlatch;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * https://www.logicbig.com/tutorials/core-java-tutorial/java-multi-threading/count-down-latch.html
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CountDownLatchExample {
	private static final int LATCH_COUNT = 4;

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(LATCH_COUNT);
		JFrame frame = createFrame();
		frame.setLayout(new FlowLayout(FlowLayout.LEFT));

		for (int i = 1; i <= LATCH_COUNT; i++) {
			ProgressThread progressThread = new ProgressThread(latch, i * 10);
			frame.add(progressThread.getProgressComponent());
			progressThread.start();
		}

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static JFrame createFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame("CountDownLatch Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(ProgressThread.PROGRESS_WIDTH, 170));
		return frame;
	}
}