package app.concurrent.bouncingball;

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
public class BouncingBall extends JPanel {

	// Box height and width
	private int width;
	private int height;

	// Ball Size
	float radius = 40;
	float diameter = radius * 2;

	// Center of Call
	float X = radius + 50;
	float Y = radius + 20;

	// Direction
	float dx = 3;
	float dy = 3;

	// Generate Ball
	DrawBallPanel ball;
	
	public BouncingBall() {

		ball = new DrawBallPanel(radius, diameter, X, Y);
		ball.repaint();
		add(ball);	
		repaint();
	}
}