package app.synchronizers.phaser;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.Phaser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
class Labka4 extends JFrame {
	Random rand = new Random();
	int x, y, r, z;
	int i = 0;

	public Labka4(int xf, int yf) {
		super("Class Paint");
		setSize(300, 100);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		x = xf;
		y = yf;
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.RED);
		g.drawString("II", x, y);
		while (i < 30) {
			if (x == 300) {
				Color randomColour = new Color(rand.nextInt(256),
						rand.nextInt(256), rand.nextInt(256));
				g.setColor(randomColour);

				while (x != 0) {
					g.drawString("II", x, y);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
					x -= 5.0;
				}
			} else {
				Color randomColour = new Color(rand.nextInt(256),
						rand.nextInt(256), rand.nextInt(256));
				g.setColor(randomColour);
				while (x < 300) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
					x += 5.0;
					g.drawString("II", x, y);
				}
			}
			i++;
		}
	}
}

class MyLabs extends JFrame {
	int x, y;
	int num = 0; // counts number of words that fit our state
	String[] SS;
	String finalTextlab1 = "";
	boolean boollab1;
	boolean boollab2;
	String word3;
	String finalTextlab3 = "";

	public synchronized void Lab1() {
		PrintWriter out = new PrintWriter(System.out);
		while (true) { // shows the enter message until user enters a text
			String S = JOptionPane.showInputDialog(null,
					"Enter your text to find palindromes:");
			while (S == null) { // shows the enter message again and again if
								// user presses 'cancel'
				JOptionPane.showMessageDialog(null,
						"ERROR: You must enter you text");
				S = JOptionPane.showInputDialog(null,
						"Enter your text to find palindromes:");
			}
			S = S.replaceAll(",", "");
			S = S.replaceAll("\\.", "");
			S = S.replaceAll(";", "");
			S = S.replaceAll(":", "");
			S = S.toLowerCase();
			SS = S.split("\\s");
			Stack stack1 = new Stack();

			try {
				if (SS[0] == null || SS[0].isEmpty()) { // throws exception if
														// user entered nothing
					num = 1;
					throw new RuntimeException(
							"UNEXPECTED VALUES: The program cannot take in an empty String or null value\n");
				} else {
					for (int i = 0; i < SS.length; i++) {
						for (int j = i + 1; j < SS.length; j++) {
							boollab1 = solve(SS[i], SS[j]); // calling a method
															// for comparing two
															// words
							if (boollab1) { // if two words are palindromes
								num++;
								String val = SS[i] + " " + SS[j] + "\n";
								stack1.push(val); // putting words in stack
							}
						}
						while (!stack1.empty())
							finalTextlab1 += stack1.pop(); // making a long text
															// out of all pairs
															// of words that are
															// palindromes
					}
					break;
				}
			} catch (RuntimeException exc) {
				System.out.println(exc.getMessage());
				System.out.println("Enter your text again:\n");
			}
		}
	} // method for finding palindromes

	public static boolean solve(String S, String S1) {
		StringBuilder SS = new StringBuilder(S);
		SS = SS.reverse(); // reverse the word
		return S1.equals(SS.toString());
	} // method that compares words, used in method Lab1

	public void resultsLab1() {
		if (num == 0) {
			JOptionPane.showMessageDialog(null,
					"Results for palindromes:\nNo such words, sorry bae...");
		} else {
			JOptionPane.showMessageDialog(null, "Results for palindromes:\n"
					+ finalTextlab1 + "\t");
		}
	}

	public synchronized void Lab2() {
		while (true) { // shows the enter message until user enters a text
			JDialog myframe2 = new JDialog();
			myframe2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			myframe2.setAlwaysOnTop(true);
			String S2 = JOptionPane.showInputDialog(myframe2,
					"Enter your data to identify IP address:");
			while (S2 == null) { // shows the enter message again and again if
									// user presses 'cancel'
				JOptionPane.showMessageDialog(null,
						"ERROR: You must enter you text");
				S2 = JOptionPane.showInputDialog(myframe2,
						"Enter your data to identify IP address:");
			}
			try {
				boollab2 = validate(S2); // calling a method that validates
											// whether a text is an IP
				break;
			} catch (RuntimeException exc) {
				JOptionPane.showMessageDialog(null, exc.getMessage());
			}
		}

	} // method for defining an IP address

	public void resultsLab2() {
		if (boollab2 == true) {
			JOptionPane.showMessageDialog(null,
					"Result fo IP:\nEntered text is an IP address");
		} else {
			JOptionPane.showMessageDialog(null,
					"Result fo IP:\nEntered text is not an IP address");
		}
	}

	public static boolean validate(String ip) {
		final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		if (ip == null || ip.isEmpty()) {
			throw new RuntimeException(
					"UNEXPECTED VALUES: The program cannot take in an empty String or null value");

		} else {
			Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
			Matcher matcher = pattern.matcher(ip);
			return matcher.matches();

		}
	} // validates String, used in Lab2

	public synchronized void Lab3() {
		while (true) { // shows the enter message until user enters a text
			JDialog myframe = new JDialog();
			myframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			myframe.setAlwaysOnTop(true);
			word3 = JOptionPane.showInputDialog(myframe,
					"Enter symbols to write words backwards:");
			while (word3 == null) { // shows the enter message again and again
									// if user presses 'cancel'
				JOptionPane.showMessageDialog(null,
						"ERROR: You must enter you text");
				word3 = JOptionPane.showInputDialog(null,
						"Enter symbols to write words backwards:");
			}
			Stack stack3 = new Stack();
			String[] sentences = word3.split("[-/%,.!?()]");

			try {
				if (sentences[0] == null || sentences[0].isEmpty()
						|| sentences[0] == "") {
					throw new RuntimeException(
							"UNEXPECTED VALUES: The program cannot take in an empty String or null value\n");
				} else {
					// notify();
					for (int i = 0; i < sentences.length; i++) {
						char array[] = sentences[i].toCharArray();

						for (int j = 0; j < array.length; j++) {
							char val = array[j];
							stack3.push(val);
						}
						while (!stack3.empty())
							finalTextlab3 += stack3.pop();
					}
					break;
				}

			} catch (RuntimeException exc) {
				JOptionPane.showMessageDialog(null, exc.getMessage());
			}
		}
	} // writing all the word from text backwards

	public void resultsLab3() {
		JOptionPane.showMessageDialog(null, "Results backwards:\n"
				+ finalTextlab3 + "\t");
	}

	public void resultsLaba4() {
		while (true) {
			String xstr = JOptionPane.showInputDialog("Enter x");
			try {
				if (xstr == null || xstr.isEmpty()) {
					throw new RuntimeException(
							"UNEXPECTED VALUES: The program cannot take in a null value\n");
				}
			} catch (RuntimeException x_run_exc) {
				JOptionPane.showMessageDialog(null, x_run_exc.getMessage());
				continue;
			}

			try {
				x = Integer.parseInt(xstr);
			} catch (NumberFormatException xexc) {
				JOptionPane
						.showMessageDialog(null,
								"UNEXPECTED VALUES: The program cannot take in a String\n");
				continue;
			}

			try {
				if (x < 0) {
					throw new RuntimeException(
							"UNEXPECTED VALUES: The program cannot take values that are lower than zero\n");
				} else {
					break;
				}
			} catch (RuntimeException x_zero_exc) {
				JOptionPane.showMessageDialog(null, x_zero_exc.getMessage());
				continue;
			}
		}

		while (true) {
			String ystr = JOptionPane.showInputDialog("Enter y");
			try {
				if (ystr == null || ystr.isEmpty()) {
					throw new RuntimeException(
							"UNEXPECTED VALUES: The program cannot take in a null value\n");
				}
			} catch (RuntimeException y_run_exc) {
				JOptionPane.showMessageDialog(null, y_run_exc.getMessage());
				continue;
			}

			try {
				y = Integer.parseInt(ystr);
			} catch (NumberFormatException yexc) {
				JOptionPane
						.showMessageDialog(null,
								"UNEXPECTED VALUES: The program cannot take in a String\n");
				continue;
			}

			try {
				if (y < 0) {
					throw new RuntimeException(
							"UNEXPECTED VALUES: The program cannot take values that are lower than zero\n");
				} else {
					break;
				}
			} catch (RuntimeException y_zero_exc) {
				JOptionPane.showMessageDialog(null, y_zero_exc.getMessage());
				continue;
			}
		}

		new Labka4(x, y);

	}
}

class Laba1 implements Runnable {
	Phaser phaser;
	String name;
	MyLabs labs;

	Laba1(Phaser p, String n, MyLabs labs) {
		this.phaser = p;
		this.name = n;
		phaser.register();
		this.labs = labs;
	}

	Laba1(MyLabs labs) {
		this.labs = labs;
	}

	@Override
	public void run() {
		System.out.println(name + " is doing phase " + phaser.getPhase());
		labs.Lab1();
		phaser.arriveAndAwaitAdvance();
		try {
			Thread.sleep(200);
		} catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println(name + " is doing phase " + phaser.getPhase());
		labs.resultsLab1();
		phaser.arriveAndAwaitAdvance();
		phaser.arriveAndDeregister();
	}
}

class Laba2 implements Runnable {
	Phaser phaser;
	String name;
	MyLabs labs;

	Laba2(Phaser p, String n, MyLabs labs) {
		this.phaser = p;
		this.name = n;
		phaser.register();
		this.labs = labs;
	}

	Laba2(MyLabs labs) {
		this.labs = labs;
	}

	@Override
	public void run() {
		System.out.println(name + " is doing phase " + phaser.getPhase());
		labs.Lab2();
		phaser.arriveAndAwaitAdvance();
		try {
			Thread.sleep(200);
		} catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println(name + " is doing phase " + phaser.getPhase());
		labs.resultsLab2();
		phaser.arriveAndAwaitAdvance();
		phaser.arriveAndDeregister();
	}
}

class Laba3 implements Runnable {
	Phaser phaser;
	String name;
	MyLabs labs;

	Laba3(Phaser p, String n, MyLabs labs) {
		this.phaser = p;
		this.name = n;
		phaser.register();
		this.labs = labs;
	}

	Laba3(MyLabs labs) {
		this.labs = labs;
	}

	@Override
	public void run() {
		System.out.println(name + " is doing phase " + phaser.getPhase());
		labs.Lab3();
		phaser.arriveAndAwaitAdvance();
		try {
			Thread.sleep(200);
		} catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println(name + " is doing phase " + phaser.getPhase());
		labs.resultsLab3();
		phaser.arriveAndDeregister();
	}
}

class Laba4 implements Runnable {
	Phaser phaser;
	String name;
	MyLabs labs;

	Laba4(Phaser p, String n, MyLabs labs) {
		this.phaser = p;
		this.name = n;
		phaser.register();
		this.labs = labs;
	}

	public void run() {
		System.out.println(name + " is doing phase " + phaser.getPhase());
		labs.resultsLaba4();
		phaser.arriveAndDeregister();
	}
}

public class PhaserDemo extends Applet {
	public static void main(String[] args) {
		Phaser phaser = new Phaser(1);
		MyLabs labs = new MyLabs();
		Laba1 laba1 = new Laba1(phaser, "Lab1", labs);
		Laba2 laba2 = new Laba2(phaser, "Lab2", labs);
		Laba3 laba3 = new Laba3(phaser, "Lab3", labs);
		new Thread(laba1).start();
		new Thread(laba2).start();
		new Thread(laba3).start();
		int phase = phaser.getPhase();
		phaser.arriveAndAwaitAdvance();
		System.out.println("Phase " + phase + " is over");
		phase = phaser.getPhase();
		phaser.arriveAndAwaitAdvance();
		System.out.println("Phase " + phase + " is over");
		Laba4 laba4 = new Laba4(phaser, "Lab4", labs);
		new Thread(laba4).start();
		phase = phaser.getPhase();
		phaser.arriveAndAwaitAdvance();
		System.out.println("Phase " + phase + " is over");
		phaser.arriveAndDeregister();
		System.out.println("Program is over");

	}
}
