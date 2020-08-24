package app.executors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ForkJoinTesting {
	public static void main(String[] args) {
		Mandelbrot mandy = new Mandelbrot();
		mandy.setSize(768, 768);
		mandy.setVisible(true);
	}
}

class Mandelbrot extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel mPanel = new JPanel();
	JToggleButton mModeButton = new JToggleButton("Thread Indication");
	HashMap<String, Integer> mThreadMap = new HashMap<>();

	public Mandelbrot() {
		super("Mandelbrot fractal (Fork-Join Testing)");

		this.add(mPanel.add(mModeButton).getParent());

		mModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Mandelbrot.this.repaint();
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		mThreadMap.clear();

		BufferedImage image = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		// Ñîçäàåì ïóë ïîòîêîâ ForkJoinPool, êîòîðûé çàïóñêàåò çàäà÷è
		// ðåêóðñèâíûõ òèïîâ:
		ForkJoinPool pool = new ForkJoinPool(); // ïî-óìîë÷àíèþ ðåçåðâèðóåòñÿ ïî
												// îäíîìó ïîòîêó íà ïðîöåññîð
		// Çàïóñêàåì ðåêóðñèâíóþ çàäà÷ó MandelbrotTask äëÿ âñåãî èçîáðàæåíèÿ
		pool.invoke(new MandelbrotTask(image, 0, image.getWidth() - 1, 0, image
				.getHeight() - 1));

		// Îòðèñîâûâàåì ïîëó÷åííîå èçîáðàæåíèå:
		mPanel.getGraphics().drawImage(image, 0, 0, null);
		mModeButton.repaint();
	}

	private class MandelbrotTask extends RecursiveAction // RecursiveAction -
															// òèï ðåêóðñèâíîé
															// çàäà÷è, íå
															// âîçâðàùàþùåé
															// çíà÷åíèÿ
	{
		private static final long serialVersionUID = 1L;
		private final double size = 3.0, offsetX = -0.7, thresholdSq = 100;
		private final int maxIterations = 30;
		private BufferedImage image;
		private int xStart, xEnd, yStart, yEnd;
		private final int taskSplitThreshold = 1024;

		MandelbrotTask(BufferedImage image, int xStart, int xEnd, int yStart,
				int yEnd) {
			this.image = image;
			this.xStart = xStart;
			this.xEnd = xEnd;
			this.yStart = yStart;
			this.yEnd = yEnd;
		}

		public void renderFull() {
			for (int x = xStart; x <= xEnd; x++) {
				for (int y = yStart; y <= yEnd; y++) {
					double r = x * size / image.getWidth() - size / 2 + offsetX;
					double i = y * size / image.getHeight() - size / 2;
					double zr = 0, zi = 0;
					int iter;
					for (iter = 0; iter < maxIterations; iter++) {
						double nzr = zr * zr - zi * zi + r;
						double nzi = 2 * zr * zi + i;
						if (nzr * nzr + nzi * nzi > thresholdSq) {
							break;
						}
						zr = nzr;
						zi = nzi;
					}
					if (mModeButton.isSelected()) { // èíäèêàöèÿ ðàáîòû ïîòîêîâ
													// íà èçîáðàæåíèè âêëþ÷åíà:
						// Ïîëó÷àåì íîìåð òåêóùåãî ïîòîêà èç îòîáðàæåíèÿ ïî
						// èìåíè ïîòîêà
						Integer thread_num = mThreadMap.get(Thread
								.currentThread().getName());
						if (thread_num == null) { // Åñëè ïîòîê åùå íå
													// çàðåãèñòðèðîâàí â
													// îòîáðàæåíèè:
							thread_num = mThreadMap.size() + 1; // Ïðèñâàèâàåì
																// åìó íîìåð
							// È äîáàâëÿåì ïîòîê â îòîáðàæåíèå ñ íîâûì íîìåðîì
							// (íà 1 áîëüøå ïðåäûäóùåãî):
							mThreadMap.put(Thread.currentThread().getName(),
									new Integer(thread_num));
						}
						// Èñïîëüçóåì íîìåð òåêóùåãî ïîòîêà äëÿ ðåãóëèðîâêè
						// ÿðêîñòè ó÷àñòêà èçîáðàæåíèÿ, îòðèñîâàííîãî ïîòîêîì:
						image.setRGB(x, y, Color.HSBtoRGB(0.5f * iter
								/ maxIterations, 1.0f,
								1.0f / (float) (thread_num.intValue())));
					} else {
						image.setRGB(x, y, Color.HSBtoRGB(0.5f * iter
								/ maxIterations, 1.0f, 1.0f));
					}
				}
			}
		}

		@Override
		protected void compute() // ìåòîä compute() ñîäåðæèò êîä ðåêóðñèâíîé
									// çàäà÷è
		{
			// Â êîäå ìû äðîáèì èçîáðàæåíèå íà êóñêè è îòðèñîâûâàåì ÷àñòü
			// èçîáðàæåíèÿ òîëüêî åñëè îíî äîñòàòî÷íî ìàëåíüêîå
			int width = xEnd - xStart, height = yEnd - yStart;
			if (width * height < taskSplitThreshold) {
				renderFull();
			} else {
				// ìåòîä invokeAll ïðèíèìàåò ñïèñîê çàäà÷, êîòîðûé ìû ïîëó÷àåì
				// ïðè ðåêóðñèâíîì äðîáëåíèè äàííîé çàäà÷è
				invokeAll(
						// Äåëèì èçîáðàæåíèå íà 4 ðàâíûõ êâàäðàíòà è
						// îòðèñîâûâàåì êàæäûé â ñâîåé çàäà÷å:
						new MandelbrotTask(image, xStart, xStart + width / 2,
								yStart, yStart + height / 2),
						new MandelbrotTask(image, xStart + width / 2 + 1, xEnd,
								yStart, yStart + height / 2),
						new MandelbrotTask(image, xStart, xStart + width / 2,
								yStart + height / 2 + 1, yEnd),
						new MandelbrotTask(image, xStart + width / 2 + 1, xEnd,
								yStart + height / 2 + 1, yEnd));
			}
		}
	}

}