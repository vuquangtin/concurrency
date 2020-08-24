package app.synchronizers.cyclicbarrier;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import concurrencies.utilities.Resource;

/**
 * 
 * https://github.com/DanielScocco/DiningPhilosophers
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Philosophers {
	// Number of philosophers, max=14
	private static final int NUMBER = 9;
	// Min and Max Sleep Interval, used by eat() and think()
	private static final int MIN_SLEEP = 500;
	private static final int MAX_SLEEP = 3000;
	// Variables used by the animation
	private static JLabel[] forks = new JLabel[15];
	private static JLabel[] philosophers = new JLabel[14];
	private static JLabel table;
	private static JLabel[] averages = new JLabel[3];
	private static JFrame frame;
	private static JPanel compPanel;
	// Threads
	private static Thread[] threads = new Thread[NUMBER];
	private static Thread controller;
	// Data structures used for synchronization
	private static volatile int[] forksArray = new int[14];
	private static volatile int meals = 0;
	private static volatile int[] philosopherStatus = new int[NUMBER];
	private static float concurrent = 0;
	private static int samples = 0;
	private static float mealsPerSecond = 0;
	private static long startTime;
	private static long currentTime;
	private static Semaphore[] forksSem = new Semaphore[NUMBER];
	private static Semaphore placesSem;
	private static volatile int[] philosophersBlock = new int[NUMBER];

	private static void createAndShowFrame() {
		// Create and set up the window.
		frame = new JFrame("Dining Philosophers");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// This will center the JFrame in the middle of the screen
		frame.setLocationRelativeTo(null);

		// Use this to find the path to files
		// System.out.println(new File("table3.jpg").getAbsolutePath());

		// create GribBagLayout and the GridBagLayout Constraints
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.BOTH;

		// Create panel and set layout
		compPanel = new JPanel();
		compPanel.setLayout(gridBag);

		// Add first row of images
		cons.gridy = 0;
		cons.gridx = 0;
		for (int i = 0; i < NUMBER; i++) {
			// Move grid if not first iteration
			if (i > 0)
				cons.gridx = cons.gridx + 1;
			// Add Fork
			forks[i] = new JLabel();
			forks[i].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/fork.jpg"));
			gridBag.setConstraints(forks[i], cons);
			compPanel.add(forks[i]);

			cons.gridx = cons.gridx + 1;

			// Add Philosopher
			philosophers[i] = new JLabel();
			philosophers[i].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person1.jpg"));
			gridBag.setConstraints(philosophers[i], cons);
			compPanel.add(philosophers[i]);

			// Add last fork
			if (i == NUMBER - 1) {
				cons.gridx = cons.gridx + 1;
				forks[i + 1] = new JLabel();
				forks[i + 1].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/fork.jpg"));
				gridBag.setConstraints(forks[i + 1], cons);
				compPanel.add(forks[i + 1]);
			}
		}

		// Add table
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 3;
		table = new JLabel();
		table.setIcon(new ImageIcon(Resource.PATH
				+ "DiningPhilosophers/table.jpg"));
		gridBag.setConstraints(table, cons);
		compPanel.add(table);
		cons.gridwidth = 2;
		cons.gridx = cons.gridx + 3;
		for (int i = 0; i < NUMBER - 1; i++) {
			table = new JLabel();
			table.setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/table2.jpg"));
			gridBag.setConstraints(table, cons);
			compPanel.add(table);
			cons.gridx = cons.gridx + 2;
		}

		// Add averages section
		String[] titles = { "Total Meals = 0", "Meals Per Second = 0",
				"Eating Concurrently = 0" };
		cons.gridx = 0;
		cons.gridy = 2;
		cons.gridwidth = 4;
		for (int i = 0; i < 3; i++) {
			averages[i] = new JLabel();
			averages[i].setText(titles[i]);
			averages[i].setFont(new Font("Serif", Font.PLAIN, 20));
			gridBag.setConstraints(averages[i], cons);
			compPanel.add(averages[i]);
			cons.gridy = cons.gridy + 1;
		}

		// Display the window.
		frame.add(compPanel);
		frame.pack();
		frame.setVisible(true);
	}

	private static void startAnimation() {
		// Initialize variables
		for (int i = 0; i < NUMBER; i++) {
			forksArray[i] = 1;
			philosopherStatus[i] = 0;
			forksSem[i] = new Semaphore(1, true);
			philosophersBlock[i] = 0;
		}

		startTime = System.nanoTime();
		placesSem = new Semaphore(NUMBER - 1, true);

		// Start controller thread
		controller = new Thread(new Controller());
		controller.start();

		// Create and start individual threads
		for (int i = 0; i < NUMBER; i++) {
			threads[i] = new Thread(new Philosopher(i));
			threads[i].start();
		}
	}

	/*********** MAIN *****************/
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowFrame();
				startAnimation();
			}
		});
	}

	/* Class that represents a single philosopher */
	private static class Philosopher implements Runnable {
		private int id;
		private int leftFork;
		private int rightFork;
		private int leftNeighbor;
		private int rightNeighbor;

		/* Choose the algorithm to run: getForks1(), getForks2(), etc. */
		public void run() {
			while (true) {
				think();
				try {
					getForks3();
				} catch (Exception e) {

				}
				eat();
				putForks3();
			}
		}

		/* Constructor */
		public Philosopher(int id) {
			this.id = id;
			this.rightFork = id;
			if (id == NUMBER - 1)
				this.leftFork = 0;
			else
				this.leftFork = id + 1;
			this.rightNeighbor = id - 1;
			if (id == 0)
				this.rightNeighbor = NUMBER - 1;
			this.leftNeighbor = (id + 1) % NUMBER;
		}

		public void think() {
			try {
				int sleepInterval = MIN_SLEEP
						+ (int) (Math.random() * ((MAX_SLEEP - MIN_SLEEP) + 1));
				Thread.sleep(sleepInterval);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		public void getForks1() {
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person2.jpg"));
			// Grab fork on the right
			while (forksArray[rightFork] == 0)
				;
			forksArray[rightFork] = 0;
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person3.jpg"));
			forks[rightFork].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/nofork.jpg"));
			if (rightFork == 0)
				forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));

			// Grab fork on the left
			while (forksArray[leftFork] == 0)
				;
			forksArray[leftFork] = 0;
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person5.jpg"));
			forks[leftFork].setIcon(new ImageIcon(
					"DiningPhilosophers/nofork.jpg"));
			if (leftFork == 0)
				forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));
		}/* end of getForks1() */

		public void getForks2() {
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person2.jpg"));
			if (id % 2 == 0) {
				// Grab fork on the right
				while (forksArray[rightFork] == 0)
					;
				forksArray[rightFork] = 0;
				philosophers[id].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/person3.jpg"));
				forks[rightFork].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));
				if (rightFork == 0)
					forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
							+ "DiningPhilosophers/nofork.jpg"));

				// Grab fork on the left
				while (forksArray[leftFork] == 0)
					;
				forksArray[leftFork] = 0;
				philosophers[id].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/person5.jpg"));
				forks[leftFork].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));
				if (leftFork == 0)
					forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
							+ "DiningPhilosophers/nofork.jpg"));
			}/* end of if */

			else {
				// Grab fork on the left
				while (forksArray[leftFork] == 0)
					;
				forksArray[leftFork] = 0;
				philosophers[id].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/person4.jpg"));
				forks[leftFork].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));
				if (leftFork == 0)
					forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
							+ "DiningPhilosophers/nofork.jpg"));

				// Grab fork on the right
				while (forksArray[rightFork] == 0)
					;
				forksArray[rightFork] = 0;
				philosophers[id].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/person5.jpg"));
				forks[rightFork].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));
				if (rightFork == 0)
					forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
							+ "DiningPhilosophers/nofork.jpg"));
			}/* end of else */
		}/* end of getForks2() */

		public void getForks3() throws InterruptedException {
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person2.jpg"));
			if (id % 2 == 0) {
				// Grab fork on the right
				forksSem[rightFork].acquire();
				forks[rightFork].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));
				if (rightFork == 0)
					forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
							+ "DiningPhilosophers/nofork.jpg"));
				philosophers[id].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/person3.jpg"));

				// Grab fork on the left
				forksSem[leftFork].acquire();
				forks[leftFork].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));
				if (leftFork == 0)
					forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
							+ "DiningPhilosophers/nofork.jpg"));
				philosophers[id].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/person5.jpg"));

			}/* end of if */

			else {
				// Grab fork on the left
				forksSem[leftFork].acquire();
				forks[leftFork].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));
				if (leftFork == 0)
					forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
							+ "DiningPhilosophers/nofork.jpg"));
				philosophers[id].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/person4.jpg"));

				// Grab fork on the right
				forksSem[rightFork].acquire();
				forks[rightFork].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));
				if (rightFork == 0)
					forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
							+ "DiningPhilosophers/nofork.jpg"));
				philosophers[id].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/person5.jpg"));

			}/* end of else */
		}/* end of getForks3() */

		public void getForks4() throws InterruptedException {
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person2.jpg"));

			placesSem.acquire();
			// Grab fork on the left
			forksSem[leftFork].acquire();
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person4.jpg"));
			forks[leftFork].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/nofork.jpg"));
			if (leftFork == 0)
				forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));

			// Grab fork on the right
			forksSem[rightFork].acquire();
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person5.jpg"));
			forks[rightFork].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/nofork.jpg"));
			if (rightFork == 0)
				forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));

		}/* end of getForks4() */

		public void getForks5() throws InterruptedException {
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person2.jpg"));

			while (philosophersBlock[id] != 0) {
				;
			}

			// Block neighbors
			philosophersBlock[leftNeighbor]++;
			philosophersBlock[rightNeighbor]++;

			// Ready to go, grab both forks
			forks[leftFork].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/nofork.jpg"));
			if (leftFork == 0)
				forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person5.jpg"));
			forks[rightFork].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/nofork.jpg"));
			if (rightFork == 0)
				forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/nofork.jpg"));

		}/* end of getForks5() */

		public void eat() {
			meals++;
			philosopherStatus[id] = 1;
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person6.jpg"));
			int eatInterval = MIN_SLEEP
					+ (int) (Math.random() * ((MAX_SLEEP - MIN_SLEEP) + 1));
			try {
				Thread.sleep(eatInterval);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		/* works with getForks() 1 and 2 */
		public void putForks1() {
			philosopherStatus[id] = 0;
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person1.jpg"));
			forks[rightFork].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/fork.jpg"));
			forks[leftFork].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/fork.jpg"));
			if (rightFork == 0 || leftFork == 0)
				forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/fork.jpg"));
			forksArray[leftFork] = 0;
			forksArray[rightFork] = 0;
		}

		public void putForks3() {
			philosopherStatus[id] = 0;
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person1.jpg"));
			forks[rightFork].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/fork.jpg"));
			forks[leftFork].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/fork.jpg"));
			if (rightFork == 0 || leftFork == 0)
				forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/fork.jpg"));
			forksSem[rightFork].release();
			forksSem[leftFork].release();
		}

		public void putForks4() {
			philosopherStatus[id] = 0;
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person1.jpg"));
			forks[rightFork].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/fork.jpg"));
			forks[leftFork].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/fork.jpg"));
			if (rightFork == 0 || leftFork == 0)
				forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/fork.jpg"));
			forksSem[rightFork].release();
			forksSem[leftFork].release();
			placesSem.release();
		}

		public void putForks5() {
			philosopherStatus[id] = 0;
			philosophers[id].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/person1.jpg"));

			forks[rightFork].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/fork.jpg"));
			forks[leftFork].setIcon(new ImageIcon(Resource.PATH
					+ "DiningPhilosophers/fork.jpg"));
			if (rightFork == 0 || leftFork == 0)
				forks[NUMBER].setIcon(new ImageIcon(Resource.PATH
						+ "DiningPhilosophers/fork.jpg"));

			// Unblock neighbors
			philosophersBlock[leftNeighbor]--;
			philosophersBlock[rightNeighbor]--;
		}

	}

	/*
	 * Thread that tracks the number of meals, time elapsed and concurrency
	 * level
	 */
	private static class Controller implements Runnable {
		public void run() {
			while (true) {
				DecimalFormat df = new DecimalFormat("#.##");

				// Update total meals
				averages[0].setText("Total meals = " + meals);

				// Update meals per second
				long diff;
				currentTime = System.nanoTime();
				diff = currentTime - startTime;
				diff = diff / 1000000000;
				float mealSecond = (float) meals / diff;
				averages[1].setText("Meals Per Second = "
						+ df.format(mealSecond));

				// Update concurrent meals
				int count = 0;
				for (int i = 0; i < NUMBER; i++) {
					if (philosopherStatus[i] == 1)
						count++;
				}
				samples++;
				concurrent = concurrent + count;
				float avg = concurrent / samples;

				averages[2].setText("Eating Concurrently = " + df.format(avg));

				if (meals > 1000) {
					System.out.println("meals/second=" + mealSecond
							+ " concurrency=" + avg);
				}

				try {
					Thread.sleep(1000);
				} catch (Exception e) {

				}
			}
		}
	}
}