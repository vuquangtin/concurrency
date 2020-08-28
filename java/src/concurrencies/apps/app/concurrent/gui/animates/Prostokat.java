package app.concurrent.gui.animates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
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

public class Prostokat {

	private List<BufferedImage> images = new ArrayList<BufferedImage>();
	private boolean animated = false;
	private int i = 0;
	private int xPos = 50;
	private int yPos = 50;
	private int vx = randomWithRange(-5, 5);
	private int vy = randomWithRange(-5, 5);
	private int width = 20;
	private int height = 20;
	private Color color = Color.BLACK;

	public void setImages(List<BufferedImage> img) {
		this.images = img;
		this.animated = true;
	}

	public int getX() {
		return xPos;
	}

	public void setX(int xPos) {
		this.xPos = xPos;
	}

	public void setY(int yPos) {
		this.yPos = yPos;
	}

	public int getY() {
		return yPos;
	}

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	public int getVy() {
		return vy;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void paint(Graphics g) {
		if (animated) {
			g.drawImage(images.get(i), xPos, yPos, null);
			if (i < 5)
				i++;
			if (i == 5)
				i = 0;
		} else {
			g.setColor(getColor());
			g.fillRect(xPos, yPos, getWidth(), getHeight());
		}
	}

	int randomWithRange(int min, int max) {
		int range = Math.abs(max - min) + 1;
		return (int) (Math.random() * range) + (min <= max ? min : max);
	}

	public void update() {
		xPos += vx;
		yPos += vy;
	}

	public void bounce(JPanel panel) {
		if (xPos + width > panel.getWidth() || xPos < 0)
			vx *= -1;
		if (yPos + height > panel.getHeight() || yPos < 0)
			vy *= -1;
	}

}