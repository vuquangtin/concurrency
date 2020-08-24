package app.synchronizers.cyclicbarrier;

import java.io.*;
import java.util.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
// ------------------------------------------------------------------------------------------

class EmptyStringException extends Exception {
	public EmptyStringException() {
	};

	@Override
	public String toString() {
		return "This should not be empty!\nRestarting...";
	}
}

class SWINGEmptyStringException extends Exception {
	public SWINGEmptyStringException() {
	};

	@Override
	public String toString() {
		JOptionPane.showMessageDialog(null,
				"This should not be empty!\nRestarting...");
		return "This should not be empty!\nRestarting...";
	}
}

// ------------------------------------------------------------------------------------------

// ================Lab2================
class lab2 extends Thread {
	private Thread t;
	static CyclicBarrier cb;

	public lab2(CyclicBarrier cb_param) {
		cb = cb_param;
	}

	public static void app() throws IOException, EmptyStringException {
		System.out.println("------------Lab2------------");
		EmptyStringException exString = new EmptyStringException();
		System.out.println("Color code validity check. Type 'x' to exit");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while (true) {
			System.out.print("Enter color code to check: ");
			line = br.readLine();
			if (line.equals(""))
				throw exString;
			else if (line.equalsIgnoreCase("x")) {
				try {
					cb.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
				break;
			} else if (line.matches("#([0-9]|[A-F]|[a-f]){6}")) {
				System.out.println("This is a color code");
			} else
				System.out.println("This is not a color code");

		}
	};

	@Override
	public void run() {
		while (true) {
			try {
				app();
				break;
			} catch (IOException | EmptyStringException e) {
				System.out.println(e.toString());
			}
		}
	}

}

// ================Lab4================
class lab4 extends JPanel implements ActionListener, Runnable {
	private Thread t;

	Timer timer;
	Random rand = new Random();
	public static String myText;
	private int x = 250;
	private final int y = 240;
	private int counter = 0;
	boolean reverse = false;
	private Color color;
	private final Color ColorArray[] = { Color.CYAN, Color.MAGENTA, Color.BLUE,
			Color.RED, Color.ORANGE };
	static CyclicBarrier cb;
	private boolean cdlTest = true;

	public lab4(CyclicBarrier cb_param) {
		this.setLayout(null);
		timer = new Timer(20, this);
		timer.setInitialDelay(190);
		timer.start();
		cb = cb_param;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g.setFont(new Font("Arial", Font.BOLD, 30));

		// Moving horizontally

		counter++;
		if (!reverse) {
			x++;
			if (x == 450) {
				reverse = true;
				color = ColorArray[rand.nextInt(5)];
			}
		} else {
			x--;
			if (x == 0) {
				reverse = false;
				color = ColorArray[rand.nextInt(5)];
			}
		}

		if (counter == 1000 && cdlTest) {
			try {
				cb.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			counter = 0;
			cdlTest = false;
		}

		g2.setColor(color);
		g2.drawString(myText, x, y);
	}

	@Override
	public void run() {
		SWINGEmptyStringException swExString = new SWINGEmptyStringException();
		JFrame frame = new JFrame("lab4");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new lab4(cb));

		while (true) {
			try {
				myText = JOptionPane.showInputDialog("Write your text here");
				if (myText.equals(""))
					throw swExString;
				break;
			} catch (SWINGEmptyStringException e) {
				e.toString();
			}
		}

		frame.setSize(640, 480);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	void start() {
		t = new Thread(this, "lab1");
		t.start();
	}
}

// --------------------------------------------------------------------------------------

public class Lab3_CyclicBarrier {

	public static void main(String[] args) {
		Runnable act = new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(null, "Barrier reached");
			}
		};

		CyclicBarrier CB = new CyclicBarrier(2, act);

		lab2 thread_lab2 = new lab2(CB);
		lab4 thread_lab4 = new lab4(CB);

		thread_lab4.start();
		try {
			thread_lab2.start();
			thread_lab2.join();
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Interrupted Exception");
		}

		System.exit(0);
	}

}
