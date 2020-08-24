package app.synchronizers.cyclicbarrier;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.concurrent.CyclicBarrier;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * https://www.logicbig.com/tutorials/core-java-tutorial/java-multi-threading/
 * cyclic-barrier.html
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CyclicBarrierExample {
	private static final int THREAD_COUNT = 4;

	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT);
		JFrame frame = createFrame();
		frame.setLayout(new FlowLayout(FlowLayout.LEFT));

		for (int i = 1; i <= THREAD_COUNT; i++) {
			ProgressThread progressThread = new ProgressThread(barrier, i * 10);
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
		JFrame frame = new JFrame("Java CyclicBarrier Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(ProgressThread.PROGRESS_WIDTH, 170));
		return frame;
	}
}