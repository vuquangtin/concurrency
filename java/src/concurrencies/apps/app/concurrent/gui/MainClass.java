package app.concurrent.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

import app.concurrent.gui.animates.PanelRysowania;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class MainClass {

	public static void main(String[] args) {
		//SwingUtilities.invokeLater(new Runnable() {

			//public void run() {
				JFrame f = new JFrame("Prostokaty");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				PanelRysowania panel = new PanelRysowania();
				
				panel.dodajProstokat(100, 100, 80, 160, Color.BLUE);

				for (int i = 1; i< 10 ; i++) panel.dodajLosowyProstokat();

				try {
					panel.dodajAnimowanyProstokat();
				} catch (IOException e) {
					e.printStackTrace();
				}



				ExecutorService exec = Executors.newFixedThreadPool(1);

				exec.execute(panel);

				f.add(panel);
				f.setSize(600, 600);
				f.setVisible(true);
				
			}
	//	});

//	}

}
