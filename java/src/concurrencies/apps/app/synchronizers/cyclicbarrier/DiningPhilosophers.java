package app.synchronizers.cyclicbarrier;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class DiningPhilosophers extends JFrame {

	private JPanel contentPane;
	private Canvas[] canvas = new Canvas[5];
	private Label[] label = new Label[5];
	private int[] ecount = new int[5];
	private JLabel lblNoOfTimes, lblPhilosopher1, lblPhilosopher2,
			lblPhilosopher3, lblPhilosopher4, lblPhilosopher5;
	private Label[] lblPhilosopher = new Label[5];
	private JLabel[] lblNewLabel = new JLabel[5];
	private JLabel lblDining;
	private JLabel lblPhilosophers;
	private JLabel lblProblem;

	public DiningPhilosophers() {
		super("Dining Philosophers!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				start();
				btnStart.setVisible(false);
			}
		});
		btnStart.setBounds(613, 41, 89, 23);
		contentPane.add(btnStart);

		canvas[0] = new Canvas();
		canvas[0].setBackground(Color.PINK);
		canvas[0].setBounds(212, 35, 100, 100);
		contentPane.add(canvas[0]);

		canvas[1] = new Canvas();
		canvas[1].setBackground(Color.PINK);
		canvas[1].setBounds(38, 159, 100, 100);
		contentPane.add(canvas[1]);

		canvas[4] = new Canvas();
		canvas[4].setBackground(Color.PINK);
		canvas[4].setBounds(382, 159, 100, 100);
		contentPane.add(canvas[4]);

		canvas[2] = new Canvas();
		canvas[2].setBackground(Color.PINK);
		canvas[2].setBounds(153, 314, 100, 100);
		contentPane.add(canvas[2]);

		canvas[3] = new Canvas();
		canvas[3].setBackground(Color.PINK);
		canvas[3].setBounds(304, 314, 100, 100);
		contentPane.add(canvas[3]);

		label[0] = new Label("Phil_1");
		label[0].setBounds(238, 141, 96, 14);
		contentPane.add(label[0]);

		label[1] = new Label("Phil_2");
		label[1].setBounds(65, 265, 96, 14);
		contentPane.add(label[1]);

		label[4] = new Label("Phil_3");
		label[4].setBounds(412, 265, 96, 14);
		contentPane.add(label[4]);

		label[2] = new Label("Phil_4");
		label[2].setBounds(177, 420, 96, 14);
		contentPane.add(label[2]);

		label[3] = new Label("Phil_5");
		label[3].setBounds(331, 414, 96, 14);
		contentPane.add(label[3]);

		lblNoOfTimes = new JLabel("Eating degree");
		lblNoOfTimes.setBounds(500, 158, 224, 49);
		contentPane.add(lblNoOfTimes);
		lblNoOfTimes.setVisible(false);

		lblPhilosopher[0] = new Label("Philosopher_1 : "
				+ Integer.toString(ecount[0]));
		lblPhilosopher[0].setBounds(520, 209, 134, 14);
		contentPane.add(lblPhilosopher[0]);
		lblPhilosopher[0].setVisible(false);

		lblPhilosopher[1] = new Label("Philosopher_2 : "
				+ Integer.toString(ecount[1]));
		lblPhilosopher[1].setBounds(520, 245, 117, 14);
		contentPane.add(lblPhilosopher[1]);
		lblPhilosopher[1].setVisible(false);

		lblPhilosopher[2] = new Label("Philosopher_3 : "
				+ Integer.toString(ecount[2]));
		lblPhilosopher[2].setBounds(520, 277, 117, 14);
		contentPane.add(lblPhilosopher[2]);
		lblPhilosopher[2].setVisible(false);

		lblPhilosopher[3] = new Label("Philosopher_4 : "
				+ Integer.toString(ecount[3]));
		lblPhilosopher[3].setBounds(520, 314, 117, 14);
		contentPane.add(lblPhilosopher[3]);
		lblPhilosopher[3].setVisible(false);

		lblPhilosopher[4] = new Label("Philosopher_5 :"
				+ Integer.toString(ecount[4]));
		lblPhilosopher[4].setBounds(520, 343, 117, 14);
		contentPane.add(lblPhilosopher[4]);
		lblPhilosopher[4].setVisible(false);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(613, 65, 89, 23);
		contentPane.add(btnExit);
		String path = concurrencies.utilities.Resource.PATH + "enable.png";
		lblNewLabel[0] = new JLabel("");
		System.out.println(path);
		lblNewLabel[0].setIcon(new ImageIcon(path));
		lblNewLabel[0].setBounds(151, 81, 8, 57);
		contentPane.add(lblNewLabel[0]);
		lblNewLabel[0].setVisible(true);

		lblNewLabel[4] = new JLabel("");
		lblNewLabel[4].setIcon(new ImageIcon(path));
		lblNewLabel[4].setBounds(354, 81, 8, 57);
		contentPane.add(lblNewLabel[4]);
		lblNewLabel[4].setVisible(true);

		lblNewLabel[1] = new JLabel("");
		lblNewLabel[1].setIcon(new ImageIcon(path));
		lblNewLabel[1].setBounds(109, 355, 8, 57);
		contentPane.add(lblNewLabel[1]);
		lblNewLabel[1].setVisible(true);

		lblNewLabel[2] = new JLabel("");
		lblNewLabel[2].setIcon(new ImageIcon(path));
		lblNewLabel[2].setBounds(268, 355, 8, 57);
		contentPane.add(lblNewLabel[2]);
		lblNewLabel[2].setVisible(true);

		lblNewLabel[3] = new JLabel("");
		lblNewLabel[3].setIcon(new ImageIcon(path));
		lblNewLabel[3].setBounds(426, 355, 8, 57);
		contentPane.add(lblNewLabel[3]);

		lblDining = new JLabel("Dining");
		lblDining.setFont(new Font("Cambria Math", Font.PLAIN, 15));
		lblDining.setBounds(238, 185, 46, 14);
		contentPane.add(lblDining);

		lblPhilosophers = new JLabel("Philosophers'");
		lblPhilosophers.setFont(new Font("Cambria Math", Font.PLAIN, 15));
		lblPhilosophers.setBounds(218, 204, 113, 14);
		contentPane.add(lblPhilosophers);

		lblProblem = new JLabel("Problem");
		lblProblem.setFont(new Font("Cambria Math", Font.PLAIN, 15));
		lblProblem.setBounds(228, 220, 67, 14);
		contentPane.add(lblProblem);
		lblNewLabel[3].setVisible(true);

		setSize(750, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void start() {

		SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {

			@Override
			protected Boolean doInBackground() throws Exception {

				for (int j = 0; j < 5; j++)
					ecount[j] = 0;

				class dine implements Runnable {
					private Thread t;
					private String threadname;
					public int stick, id1, curr[], which[];
					public String status;
					final CyclicBarrier gate = new CyclicBarrier(5);

					dine(String name, int id, int[] a, int[] b, int n) {
						stick = n - 1;
						id1 = id;
						threadname = name;
						curr = a;
						which = b;
						status = "thinking!!";
						canvas[id1].setBackground(Color.MAGENTA);
						lblNoOfTimes.setVisible(true);
						lblPhilosopher[0].setVisible(true);
						lblPhilosopher[1].setVisible(true);
						lblPhilosopher[2].setVisible(true);
						lblPhilosopher[3].setVisible(true);
						lblPhilosopher[4].setVisible(true);

						// System.out.println("Creating"+threadname);
					}

					public void thinking(int i) throws InterruptedException {
						Thread.sleep(5000);
						System.out.println(Integer.toString(id1)
								+ " is thnking");
						status = "thinking";
						label[i].setText(status);
					}

					public int pickup(int i) throws InterruptedException {
						/*
						 * if(i==4) { for(int j=0;j<5;j++){
						 * System.out.println("problem        "
						 * +curr[j]+" 111person-"+which[j]); } }
						 */
						// canvas[i].setBackground(Color.WHITE);
						int a = 0;
						if (i > 0) {
							if (curr[i - 1] != 1) {
								curr[i - 1] = 1;
								which[i - 1] = i;
								lblNewLabel[i - 1].setVisible(false);

							}
							if (which[i - 1] == i && curr[i] != 1) {
								curr[i] = 1;
								which[i] = i;
								System.out.println("The person picked is"
										+ Integer.toString(id1));
								/*
								 * for(int j=0;j<5;j++) {
								 * System.out.println(i+"  111stick_state-"
								 * +curr[j]+" 111person-"+which[j]); }
								 */
								a = 2;
								status = "Chopstick picked!";
								canvas[i].setBackground(Color.YELLOW);
								label[i].setText(status);
								lblNewLabel[i].setVisible(false);
							} else {
								if (which[i - 1] == i && curr[i - 1] == 1) {
									which[i - 1] = -1;
									curr[i - 1] = 0;
									// lblNewLabel[i-1].setVisible(true);
									// lblNewLabel[i].setVisible(true);
									status = "Hungry";
									label[i].setText(status);
								}
								canvas[i].setBackground(Color.RED);
							}

						}
						if (i == 0) {
							if (curr[stick] != 1) {
								curr[stick] = 1;
								which[stick] = i;
								// lblNewLabel[stick].setVisible(false);
								// lblNewLabel[stick].revalidate();
								System.out
										.println("                   entered  "
												+ stick + "\n");
							}
							if (which[stick] == i && curr[i] != 1) {
								curr[i] = 1;
								which[i] = i;

								lblNewLabel[i].setVisible(false);
								System.out.println("The person picked is"
										+ Integer.toString(i));
								for (int j = 0; j <= stick; j++) {
									System.out.println(id1
											+ "  111stick_state-" + curr[j]
											+ " 111person-" + which[j]);
								}
								a = 2;
								status = "Chopstick picked!!";
								canvas[i].setBackground(Color.YELLOW);
								label[i].setText(status);
							} else {
								if (which[stick] == i && curr[stick] != 1) {
									curr[stick] = 0;
									which[stick] = -1;
									// lblNewLabel[stick].setVisible(true);
									// lblNewLabel[i].setVisible(true);
									status = "Hungry";
									label[0].setText(status);
								}
							}
						}
						Thread.sleep(500);
						return a;
					}

					public void eat(int i) throws InterruptedException {
						if (i == 0) {
							lblNewLabel[0].setVisible(false);
							lblNewLabel[4].setVisible(false);
						} else {
							lblNewLabel[i].setVisible(false);
							lblNewLabel[i - 1].setVisible(false);
						}
						System.out
								.println(Integer.toString(id1) + " is eating");
						status = "eating";
						for (int j = 0; j <= stick; j++) {
							System.out.println(id1 + " stick_state-" + curr[j]
									+ " person-" + which[j]);
						}
						label[i].setText(status);
						canvas[i].setBackground(Color.GREEN);
						Thread.sleep(5000);
						ecount[i]++;
						lblPhilosopher[i].setText("Philosopher_"
								+ Integer.toString(i + 1) + " : "
								+ Integer.toString(ecount[i]));
					}

					public void putdown(int i) throws InterruptedException {
						status = "Done";
						if (which[i] == i) {
							which[i] = -1;
							curr[i] = 0;
							// lblNewLabel[i].setVisible(true);
						}
						if (i > 0 && which[i - 1] == i) {
							which[i - 1] = -1;
							curr[i - 1] = 0;
							// lblNewLabel[i-1].setVisible(true);
						}
						if (i == 0 && which[stick] == i) {
							which[stick] = -1;
							curr[stick] = 0;
							// lblNewLabel[stick].setVisible(true);
						}
						System.out.println(Integer.toString(id1)
								+ " dropped the chopsticks");
						label[i].setText(status);
						canvas[i].setBackground(Color.GRAY);
						for (int j = 0; j <= stick; j++) {
							System.out.println("dropppp   " + id1
									+ " stick_state-" + curr[j] + " person-"
									+ which[j]);
						}
						if (i > 0) {
							thinking(i - 1);
							if (pickup(i - 1) == 2) {
								eat(i - 1);
								putdown(i - 1);
							}
						} else {
							thinking(stick);
							if (pickup(stick) == 2) {
								eat(stick);
								putdown(stick);
							}
						}
						Thread.sleep(5000);

					}

					public void run() {
						System.out.println("running " + threadname);
						int i = 0;
						while (i < 2) {
							try {

								thinking(id1);
								if (pickup(id1) == 2) {
									eat(id1);
									putdown(id1);
								}
							} catch (InterruptedException e) {
								System.out.println("Interrupted" + threadname);
							}
							i++;
						}

						System.out.println("exiting" + threadname);

					}

					public void start() {
						int i = 0;
						System.out.println("Starting " + threadname);
						if (t == null) {
							t = new Thread(this, threadname);

							t.start();
							// gate.await();
							i++;
						}
					}
				}
				dine[] a;
				int stick, id1;
				int curr[];
				int which[];

				a = new dine[100];
				curr = new int[100];
				which = new int[100];

				for (int i = 0; i < 100; i++) {
					curr[i] = 0;
					which[i] = -1;
				}
				int n = 5;

				for (int i = 0; i < n; i++) {
					Thread.sleep(100);
					a[i] = new dine(Integer.toString(i), i, curr, which, n);
					a[i].start();
					System.out
							.println("kjasdbasdvadgfakhbfakghdfadvhdsfvhgdf    "
									+ i + a[i].status);
					publish(i);
				}
				return true;

			}

			protected void done() {
				boolean status;
				try {
					status = get();
				} catch (InterruptedException e) {
				} catch (ExecutionException e) {
				}
			}

			@Override
			protected void process(List<Integer> chunks) {
				int mostRecentValue = chunks.get(chunks.size() - 1);
			}

		};

		worker.execute();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new DiningPhilosophers();
			}
		});
	}
}