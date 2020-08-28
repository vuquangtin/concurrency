package app.synchronizers.phaser;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.Phaser;

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
class EmptyStringException extends Exception {
	public EmptyStringException() {
	};

	@Override
	public String toString() {
		return "This should not be empty!\nRestarting...";
	}
}

// ------------------------------------------------------------------------------------------

// ================Lab2================
class lab2 extends Thread {
	private Thread t;
	static Phaser ph;

	public lab2(Phaser ph_param) {
		ph = ph_param;
	}

	public static void app() throws IOException, EmptyStringException {
		System.out.println("------------Lab2------------");
		EmptyStringException exString = new EmptyStringException();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		for (int i = 0; i < 3; i++) {
			System.out.print("Enter color code to check: ");
			line = br.readLine();
			if (line.equals(""))
				throw exString;
			else if (line.matches("#([0-9]|[A-F]|[a-f]){6}"))
				System.out.println("This is a color code");
			else
				System.out.println("This is not a color code");

			ph.arriveAndAwaitAdvance();
			JOptionPane.showMessageDialog(null, "Phase " + (i + 1)
					+ " complete");
		}
		JOptionPane.showMessageDialog(null, "Three phases were completed");
		ph.arriveAndDeregister();
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
	private int x = 250;
	private final int y = 240;
	private int counter = 0;
	boolean reverse = false;
	private Color color;
	private final Color ColorArray[] = { Color.CYAN, Color.MAGENTA, Color.BLUE,
			Color.RED, Color.ORANGE };
	static Phaser ph;
	private final int phTest = 0;
	private String buf = "", myText = "MovingText";

	public lab4(Phaser ph_param) {
		this.setLayout(null);
		timer = new Timer(20, this);
		timer.setInitialDelay(190);
		timer.start();
		ph = ph_param;
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

		if (counter == 499 && phTest < 3) {
			buf = myText;
			myText = "Waiting...";
		}

		if (counter == 500 && phTest < 3) {
			ph.arriveAndAwaitAdvance();
			counter = 0;
			myText = buf;
		}

		if (phTest == 2)
			ph.arriveAndDeregister();

		g2.setColor(color);
		g2.drawString(myText, x, y);
	}

	@Override
	public void run() {
		JFrame frame = new JFrame("lab4");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new lab4(ph));

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

public class Lab3_Phaser {

	public static void main(String[] args) {
		Phaser PH = new Phaser(2);

		lab2 thread_lab2 = new lab2(PH);
		lab4 thread_lab4 = new lab4(PH);

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