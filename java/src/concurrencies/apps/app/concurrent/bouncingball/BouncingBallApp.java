package app.concurrent.bouncingball;

import java.awt.Color;

import javax.swing.JFrame;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class BouncingBallApp {

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Bouncing Ball");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setContentPane(new BouncingBallPanel());
		BouncingBallPanel bb = new BouncingBallPanel(20, Color.YELLOW);
		frame.setContentPane(bb);
		frame.setVisible(true);
	}

}