package app.concurrent.gui;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * https://ru.stackoverflow.com/a/1100743/188366
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class MoveAlongLineAnimation {

	public static void main(String[] args) {
		List<Point> points = new ArrayList<>();
		for (int i = 0; i < 300; i++)
			points.add(new Point(i,
					100 + (int) (Math.sin(((float) i) / 10) * 50)));

		JComponent comp = new JComponent() {
			int index = 0;

			@Override
			protected void paintComponent(Graphics g) {
				index = (index + 1) % points.size();
				Point p = points.get(index);
				((Graphics2D) g).setRenderingHint(
						RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g.drawOval(p.x - 10, p.y - 10, 20, 20);
			}
		};

		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
				() -> comp.repaint(), 0, 30, TimeUnit.MILLISECONDS);

		JFrame frame = new JFrame();
		frame.add(comp);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(300, 300));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}