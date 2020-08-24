package app.concurrent.fibonacci;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class FibonacciNumbers extends JFrame {

	// components for calculating the Fibonacci of a user-entered number
	private final JPanel workerPanel = new JPanel(new GridLayout(2, 2, 5, 5));
	private final JTextField numberJTextField = new JTextField();
	private final JButton goJButton = new JButton("Go");
	private final JLabel fibonacciJLabel = new JLabel();

	// components and variables for getting the next Fibonacci number
	private final JPanel eventThreadPanel = new JPanel(new GridLayout(2, 2, 5,
			5));
	private long n1 = 0; // initialize with first fibonacci number
	private long n2 = 1; // initialize with second fibonacci nymber;
	private int count = 1; // current fibonacci number to display
	private final JLabel nJLabel = new JLabel("Fibonacci of 1: ");
	private final JLabel nFibonacciJLabel = new JLabel(String.valueOf(n2));
	private final JButton nextNumberJButton = new JButton("Next Number");

	// constructor
	public FibonacciNumbers() {
		super("Fibonacci Numbers");
		setLayout(new GridLayout(2, 1, 10, 10));

		// add GUI components to the SwingWorker panel
		workerPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK),
				"With SwingWorker"));
		workerPanel.add(new JLabel("Get fibonacci of:"));
		workerPanel.add(numberJTextField);
		goJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int n;

				try {
					// retrieve user's input as integer
					n = Integer.parseInt(numberJTextField.getText());
				} catch (NumberFormatException nf) {
					// display an error message if the user did not enter an
					// integer
					fibonacciJLabel.setText("Enter an integer.");
					return;
				}

				// indicate that the calculation has began
				fibonacciJLabel.setText("Calculating ...");

				// create a task to perform calculation in background
				BackgroundCalculator task = new BackgroundCalculator(n,
						fibonacciJLabel);
				task.execute(); // execute the task
			}
		});

		workerPanel.add(goJButton);
		workerPanel.add(fibonacciJLabel);

		// add GUI components to the event-dispatching thread panel
		eventThreadPanel.setBorder(new TitledBorder(
				new LineBorder(Color.BLACK), "Without SwingWorker"));
		eventThreadPanel.add(nJLabel);
		eventThreadPanel.add(nFibonacciJLabel);
		nextNumberJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// calculate the fibonacci number after n2
				long temp = n1 + n2;
				n1 = n2;
				n2 = temp;
				++count;

				// display the next fibonacci number
				nJLabel.setText("Fibonacci of " + count + ": ");
				nFibonacciJLabel.setText(String.valueOf(n2));
			}
		});

		eventThreadPanel.add(nextNumberJButton);

		add(workerPanel);
		add(eventThreadPanel);
		setSize(275, 200);
		setVisible(true);
	}

	// main method begin program execution
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FibonacciNumbers application = new FibonacciNumbers();
				application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}
}
