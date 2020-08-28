package app.concurrent.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;
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
public class BloodPanel extends JPanel{
	
	public int blood=500;
	
	public BloodPanel() {
		
	}//end constructor
	
	public void hitRoad() {
		blood-=10;
		repaint();
	}//end hitroad
	
	public void hitWall() {
		blood-=100;
		repaint();
	}//end hitWall
	
	public void hitHeart() {
		blood+=50;
		repaint();
	}//end hitHeart

	public void idleRoad() {
		blood-=10;
		repaint();
	}//end idleRoad
	
	public void idleWall() {
		blood-=100;
		repaint();
	}//end idleWall
	
	public void relife() {
		JOptionPane.showMessageDialog(null, "oh!you are die!", "die", JOptionPane.DEFAULT_OPTION);
		blood=500;
		repaint();
	}//end relife
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLUE);
		g2.setStroke(new BasicStroke(25));
		//g2.drawLine(0, 10, Main.mazeFrame.getWidth()-(), 10);
		//g2.drawLine(0, 0,blood, 0);
		g2.drawRect(0, 0, blood, 25);
		
	}
}//end class BloodPanel