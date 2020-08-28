package app.concurrent.gui;

import javax.swing.JFrame;

/**
 * https://github.com/jimmyliaoviva/advancedjavaMaze/tree/master/src
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class MazeFrameMain {
	public static MazeFrame mazeFrame;

	public static void main(String[] args) {

		mazeFrame = new MazeFrame();
		mazeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mazeFrame.setSize(500, 600);
		mazeFrame.setVisible(true);
	}// end main

}// end class main