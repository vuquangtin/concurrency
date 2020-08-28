package app.concurrent.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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

public class DraftingBoard extends JPanel {
	ArrayList<Pencil> pencils = new ArrayList<Pencil>();
	ArrayList<Rubber> rubbers = new ArrayList<Rubber>();
	ArrayList<Line> lines = new ArrayList<Line>();
	ArrayList<RectangleOrCircle> rectangles = new ArrayList<RectangleOrCircle>();
	ArrayList<RectangleOrCircle> circles = new ArrayList<RectangleOrCircle>();
	ArrayList<Color> lineColors = new ArrayList<Color>();
	Pencil pencil;
	RectangleOrCircle rectangle;
	RectangleOrCircle circle;
	Line line;
	Point drawStart, drawEnd;
	Color lineColor;
	int option;
	int lineWidth;

	public DraftingBoard(int lineWidth) {

		this.lineWidth = lineWidth;
		this.setBackground(Color.WHITE);

		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (option == 1) {
					pencil = new Pencil();
					pencil.addPoint(e.getX(), e.getY());
				}
				if (option != 1) {
					drawStart = new Point(e.getX(), e.getY());
					if (option == 2)
						rubbers.add(new Rubber(drawStart));
					drawEnd = drawStart;
					if (option == 3)
						rectangle = new RectangleOrCircle(drawStart, drawEnd);
					if (option == 4)
						line = new Line(drawStart, drawEnd);
					if (option == 5)
						circle = new RectangleOrCircle(drawStart, drawEnd);
				}
				lineColors.add(lineColor);
				repaint();
			}

			public void mouseReleased(MouseEvent e) {
				drawEnd = new Point(e.getX(), e.getY());
				if (option == 3) {
					rectangle.getPoint(drawEnd);
					rectangles.add(rectangle);
				}
				if (option == 4) {
					line.getPoint(drawEnd);
					lines.add(line);
				}
				if (option == 5) {
					circle.getPoint(drawEnd);
					circles.add(circle);
				}
				// lineColors.add(lineColor);
				repaint();
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (option == 1) {
					pencil.addPoint(e.getX(), e.getY());
					pencils.add(pencil);
				}
				drawEnd = new Point(e.getX(), e.getY());
				if (option == 2)
					rubbers.add(new Rubber(drawEnd));
				if (option == 3) {
					rectangle.getPoint(drawEnd);
					rectangles.add(rectangle);
				}
				if (option == 4) {
					line.getPoint(drawEnd);
					lines.add(line);
				}
				if (option == 5) {
					circle.getPoint(drawEnd);
					circles.add(circle);
				}
				repaint();
			}
		});
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		// g2d.setColor(lineColor);

		BasicStroke bs1 = new BasicStroke(lineWidth);
		for (Color color : lineColors)
			g2d.setColor(color);
		g2d.setStroke(bs1);
		for (Pencil pencil : pencils)
			pencil.drawPencil(g);
		for (RectangleOrCircle rec : rectangles)
			rec.drawRectangles(g);
		for (Line line : lines)
			line.drawLines(g);
		for (RectangleOrCircle circle : circles)
			circle.drawCircle(g);
		for (Rubber rubber : rubbers) {
			g2d.setColor(Color.white);
			rubber.drawRubber(g);
		}
	}

	public class Pencil {
		private List<Integer> xList;
		private List<Integer> yList;

		public Pencil() {
			xList = new ArrayList<Integer>();
			yList = new ArrayList<Integer>();
		}

		public void addPoint(int x, int y) {
			xList.add(x);
			yList.add(y);
		}

		public void drawPencil(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			for (int i = 0; i < xList.size() - 1; ++i) {
				g2d.drawLine(xList.get(i), yList.get(i), xList.get(i + 1),
						yList.get(i + 1));
			}
		}
	}

	public class Rubber {
		Point drawStart;

		public Rubber(Point drawStart) {
			this.drawStart = drawStart;

		}

		public void drawRubber(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.fillOval(drawStart.x - 10, drawStart.y - 10, 10, 10);
		}
	}

	public class Line {
		Point drawStart, drawEnd;

		public Line(Point drawStart, Point drawEnd) {
			this.drawStart = drawStart;
			this.drawEnd = drawEnd;
		}

		public void getPoint(Point point) {
			drawEnd = point;
		}

		public void drawLines(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawLine(drawStart.x, drawStart.y, drawEnd.x, drawEnd.y);
		}
	}

	public class RectangleOrCircle {
		Point drawStart, drawEnd;
		private int squareW = 0;
		private int squareH = 0;
		int x, y;

		public RectangleOrCircle(Point drawStart, Point drawEnd) {
			this.drawStart = drawStart;
			this.drawEnd = drawEnd;
		}

		public void getPoint(Point point) {
			drawEnd = point;

			x = Math.min(drawStart.x, drawEnd.x);
			y = Math.min(drawStart.y, drawEnd.y);
			squareW = Math.abs(drawStart.x - drawEnd.x);
			squareH = Math.abs(drawStart.y - drawEnd.y);
		}

		public void drawRectangles(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawRect(x, y, squareW, squareH);
		}

		public void drawCircle(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawOval(x, y, squareW, squareH);
		}
	}

	public void setNumber(int option) {
		this.option = option;
	}

	public void setColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public void setWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}
}