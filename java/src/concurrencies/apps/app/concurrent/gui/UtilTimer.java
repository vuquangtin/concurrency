package app.concurrent.gui;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class UtilTimer  extends JFrame{
    Random rand = new Random();
    
    public UtilTimer() throws HeadlessException {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JButton autoPress = new JButton();
        autoPress.setText("Zmieniam sam kolor!");
        add(autoPress);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {  //InvokeLater
                    @Override
                    public void run() {
                        autoPress.setBackground(new Color(rand.nextInt()));
                    }
                });
            }
        }, 250, 250 );
        setSize(200, 300);
    }
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				 UtilTimer noClick = new UtilTimer();
			        noClick.setVisible(true);
			}
		});
    	
       
    }
}