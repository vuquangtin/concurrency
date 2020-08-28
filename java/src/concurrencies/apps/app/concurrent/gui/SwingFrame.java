package app.concurrent.gui;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */


public class SwingFrame extends JFrame {
	Random rand = new Random();
	JLabel matrix;
	JPanel colorfulPanel;
	public SwingFrame() throws HeadlessException {
		// TODO Auto-generated constructor stub
		setSize(500,100);
		this.setLayout(new FlowLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		matrix = new JLabel();
		colorfulPanel = new JPanel();
		Timer timer = new Timer(true);
		this.add(matrix);
		this.add(colorfulPanel);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						System.exit(1);
					}
					
				});
			}
		},20000);
		timer.scheduleAtFixedRate(new TimerTask() {
			String text = new String();
			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						text = "" + rand.nextInt(10);
						for(int i = 1; i<30; i++) {
							
							text = text + rand.nextInt(10);
						}
						matrix.setText(text);
					}
					
				});

			}
		}, 0, 100);
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						colorfulPanel.setBackground(new Color(rand.nextInt()));
					}
					
				});

			}
		}, 0, 100);
		timer.scheduleAtFixedRate(new TimerTask() {
			int i = 0;
			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						i++;
						SwingFrame.this.setTitle("Czas: " + i);
					}
					
				});

			}
		}, 0, 1000);

		
		
		
		
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SwingFrame frame = new SwingFrame();
				frame.setVisible(true);
				
				
			}});


	}

}

//final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
//
//scheduler.schedule( new Runnable() {
//	@Override
//	public void run(){
//		scheduler.shutdownNow();
//		System.exit(0);
//	}
//
//
//}, 20, SECONDS);