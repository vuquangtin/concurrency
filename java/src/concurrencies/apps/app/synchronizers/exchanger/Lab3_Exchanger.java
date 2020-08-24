package app.synchronizers.exchanger;

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
import java.util.concurrent.Exchanger;

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
	static Exchanger<String> ex;

	public lab2(Exchanger<String> ex_param) {
		ex = ex_param;
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
			else if (line.equalsIgnoreCase("x"))
				break;
			else if (line.matches("#([0-9]|[A-F]|[a-f]){6}")) {
				System.out.println("This is a color code");
				try {
					ex.exchange("This is a color code");
				} catch (InterruptedException e) {
					System.out.println(e);
				}

			} else {
				System.out.println("This is not a color code");
				try {
					ex.exchange("This is not a color code");
				} catch (InterruptedException e) {
					System.out.println(e);
				}

			}
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
	private final Random rand = new Random();
	public static String myText = "";
	private final int x = 150;
	private int y = 240;
	private int counter = 0;
	private boolean reverse = false;
	private Color color;
	private final Color ColorArray[] = { Color.CYAN, Color.MAGENTA, Color.BLUE,
			Color.RED, Color.ORANGE };
	static Exchanger<String> ex;

	public lab4(Exchanger<String> ex_param) {
		this.setLayout(null);
		timer = new Timer(20, this);
		timer.setInitialDelay(190);
		timer.start();
		ex = ex_param;
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

		// Moving Vertically

		counter++;
		if (!reverse) {
			y++;
			if (y == 440) {
				reverse = true;
				color = ColorArray[rand.nextInt(5)];
			}
		} else {
			y--;
			if (y == 20) {
				reverse = false;
				color = ColorArray[rand.nextInt(5)];
			}
		}

		if (counter == 299)
			myText = "";

		if (counter == 300)
			try {
				myText = ex.exchange(myText);
				counter = 0;
			} catch (InterruptedException e) {
				System.out.println(e);
			}

		g2.setColor(color);
		g2.drawString(myText, x, y);
	}

	@Override
	public void run() {
		JFrame frame = new JFrame("lab4");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new lab4(ex));

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

public class Lab3_Exchanger {
	public static void main(String[] args) {
		Exchanger<String> ex = new Exchanger();
		lab2 thread_lab2 = new lab2(ex);
		lab4 thread_lab4 = new lab4(ex);

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