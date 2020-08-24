package app.executors;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * https://github.com/Gendalfion/JavaExamples/blob/master/src/java_api_testing/
 * java_thread_api/ExecutorsTesting.java
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ExecutorsTesting {

	Random mRndGenerator = new Random(System.currentTimeMillis());

	public ExecutorsTesting() {
		JFrame myFrame = new JFrame("Executors Testing");
		myFrame.setSize(700, 800);
		myFrame.setLocationRelativeTo(null);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel main_panel = new JPanel(new BorderLayout());
		JPanel bottom_panel = new JPanel(new FlowLayout());
		main_panel.add(bottom_panel, BorderLayout.SOUTH);
		JPanel center_panel = new JPanel(new GridLayout(0, 1));
		main_panel.add(center_panel, BorderLayout.CENTER);

		new FixedThreadPoolTesting(center_panel);
		new CachedThreadPoolTesting(center_panel);
		new ScheduledThreadPoolTesting(center_panel);
		new CompletionServiceTesting(center_panel);

		myFrame.add(main_panel);
		myFrame.setVisible(true);
	}

	public static void main(String[] args) {
		new ExecutorsTesting();
	}

	// ------------------------------------------------------------------------------------------------------
	// Êëàññ, äåìîíñòðèðóþùèé ðàáîòó ïðîñòîãî ïóëà äëÿ çàäà÷ FixedThreadPool
	private class FixedThreadPoolTesting {
		ExecutorService mExecutor = Executors.newFixedThreadPool(3); // Ñîçäàåì
																		// ïóë
																		// íà 3
																		// çàäà÷è
		// Äàííûé ïóë âûïîëíÿåò ïåðåäàííûå åìó îáúåêòû òèïà Runnable èëè
		// Callable ìàêñèìóì ïî 3 øòóêè îäíîâðåìåííî
		int mThreadsTotal = 0;
		final String THREAD_NAME_PREFIX = "Thread #";

		FixedThreadPoolTesting(JPanel parent_panel) {
			JPanel border_panel = new JPanel(new BorderLayout());
			parent_panel.add(border_panel);

			JPanel left_border = new JPanel();
			border_panel.add(left_border, BorderLayout.WEST);
			final JButton start_thread_btn = new JButton("Start "
					+ THREAD_NAME_PREFIX + "1");
			left_border.add(start_thread_btn);

			JPanel right_border = new JPanel();
			border_panel.add(right_border, BorderLayout.CENTER);
			final JTextArea thread_states_tout = new JTextArea();
			thread_states_tout.setEditable(false);
			border_panel.add(new JScrollPane(thread_states_tout),
					BorderLayout.CENTER);

			JPopupMenu clear_menu = new JPopupMenu();
			clear_menu.add(new JMenuItem("Clear")).addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							thread_states_tout.setText("");
						}
					});
			thread_states_tout.setComponentPopupMenu(clear_menu);

			start_thread_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// Ïî íàæàòèþ êíîïêè äîáàâëÿåì íîâûé ïîòîê â ïóë:
					mExecutor.execute(new Runnable() {
						@Override
						public void run() {
							try {
								thread_states_tout
										.append("+++ Thread Started: "
												+ Thread.currentThread()
														.getName() + "\n\r");
								Thread.sleep(mRndGenerator.nextInt(4500) + 500);
								thread_states_tout.append("--- Thread Ended: "
										+ Thread.currentThread().getName()
										+ "\n\r");
							} catch (InterruptedException e) {
							}
						}
					});

					start_thread_btn.setText("Start " + THREAD_NAME_PREFIX
							+ Integer.toString(++mThreadsTotal + 1));
				}
			});
		} // FixedThreadPoolTesting ( JPanel parent_panel )
	} // private class FixedThreadPoolTesting

	// ------------------------------------------------------------------------------------------------------
	// Êëàññ, äåìîíñòðèðóþùèé ðàáîòó êåøèðîâàííîãî ïóëà äëÿ çàäà÷
	// CachedThreadPool
	private class CachedThreadPoolTesting {
		ExecutorService mExecutor = Executors.newCachedThreadPool(); // Ñîçäàåì
																		// êåøèðîâàííûé
																		// ïóë
		// Äàííûé ïóë âûïîëíÿåò ïåðåäàííûå åìó îáúåêòû òèïà Runnable èëè
		// Callable áåç îãðàíè÷åíèé íà êîëè÷åñòâî
		// ïåðåäàííûõ çàäà÷; ïëþñ çàâåðøåííûå ïîòîêè êåøèðóþòñÿ íà íåêîòîðîå
		// âðåìÿ è ãîòîâû äëÿ ïîâòîðíîãî èñïîëüçîâàíèÿ â ïóëå

		JTextArea mCalcResultsArea = new JTextArea();
		JTextField mCalcParamField = new JTextField("2", 10);

		CachedThreadPoolTesting(JPanel parent_panel) {
			JPanel border_panel = new JPanel(new BorderLayout());
			parent_panel.add(border_panel);

			JPanel left_border = new JPanel();
			left_border.setLayout(new BoxLayout(left_border, BoxLayout.Y_AXIS));
			border_panel.add(left_border, BorderLayout.WEST);

			final JButton start_thread_btn = new JButton("Start Calculation");
			left_border.add(start_thread_btn);

			JPanel tmp_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			tmp_panel.add(mCalcParamField);
			left_border.add(tmp_panel);

			JPanel right_border = new JPanel();
			border_panel.add(right_border, BorderLayout.CENTER);
			mCalcResultsArea.setEditable(false);
			border_panel.add(new JScrollPane(mCalcResultsArea),
					BorderLayout.CENTER);

			JPopupMenu clear_menu = new JPopupMenu();
			clear_menu.add(new JMenuItem("Clear")).addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							mCalcResultsArea.setText("");
						}
					});
			mCalcResultsArea.setComponentPopupMenu(clear_menu);

			start_thread_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// Ïî íàæàòèþ êíîïêè ñîçäàåì çàäà÷ó è ïîìåùàåì å¸ â ïóë:
					Integer calc_param = 0;
					try {
						calc_param = Integer.valueOf(mCalcParamField.getText());
					} catch (Exception e) {
					}
					// Ñîçäàåì çàäà÷ó, âîçâðàùàþùóþ ðåçóëüòàò â âèäå îáúåêòà
					// òèïà Integer
					final Future<Integer> calc_future_result = mExecutor
							.submit(new CalculationThread(calc_param));

					final String param_equation = calc_param + " + "
							+ calc_param;
					// Âûïîëíÿåì îæèäàíèå âûïîëíåíèÿ çàäà÷è â îòäåëüíîì ïîòîêå:
					mExecutor.execute(new Thread() {
						@Override
						public void run() {
							try {
								mCalcResultsArea
										.append("Waiting for equation: "
												+ param_equation + "\n\r");
								Integer int_result = calc_future_result.get(); // Ìåòîä
																				// Future<>.get
																				// áëîêèðóåò
																				// ïîòîê
																				// äî
																				// ìîìåíòà
																				// ïîêà
																				// çàäà÷à
																				// íå
																				// âåðíåò
																				// çíà÷åíèå
								mCalcResultsArea.append("Result ("
										+ param_equation + ") = " + int_result
										+ "\n\r");
							} catch (Exception ex) {
							}
						}
					});
				}
			});
		}

		// Ñîçäàåì ïðîñòîé êëàññ, èïëåìåíòèðóþùèé èíòåðôåéñ Callable<> äëÿ
		// ðåàëèçàöèè çàäà÷è, âîçâðàùàþùåé çíà÷åíèå
		class CalculationThread implements Callable<Integer> {
			Integer mParam;

			public CalculationThread(Integer param) {
				mParam = param;
			}

			@Override
			public Integer call() throws Exception {
				mCalcResultsArea.append("* Start Calculating: " + mParam
						+ " + " + mParam + "\n\r");
				mCalcResultsArea.repaint();
				try {
					Thread.sleep(mRndGenerator.nextInt(4500) + 500);
				} catch (InterruptedException e) {
				}
				mCalcResultsArea.append("* Calculation done: " + mParam + " + "
						+ mParam + "\n\r");
				mCalcResultsArea.repaint();
				return mParam + mParam;
			}
		}
	}

	// ------------------------------------------------------------------------------------------------------
	// Êëàññ, äåìîíñòðèðóþùèé ðàáîòó ïëàíèðîâùèêà çàäà÷ ScheduledThreadPool
	private class ScheduledThreadPoolTesting {
		ScheduledExecutorService mScheduledExecutor = Executors
				.newSingleThreadScheduledExecutor(); // - îäíîïîòî÷íûé
														// ïëàíèðîâùèê
		// = Executors.newScheduledThreadPool(int); // - äëÿ ìíîãîïîòî÷íîé
		// ðåàëèçàöèè ïëàíèðîâùèêà
		// Ñîçäàåì ïóë çàïëàíèðîâàííûõ çàäà÷
		// Äàííûé ïóë ïîçâîëÿåò âûïîëíÿòü çàäà÷è, îòëîæåííûå íà îïðåäåëåííûé
		// ïåðèîä âî âðåìåíè,
		// ëèáî âûïîëíÿòü çàäà÷è ñ çàäàííîé ïåðèîäè÷íîñòüþ

		JTextArea mTextArea = new JTextArea();
		JTextField mTaskDelayField = new JTextField(Integer.toString(10), 6);

		int mTaskPeriod = 15;
		JTextField mTaskPeriodField = new JTextField(
				Integer.toString(mTaskPeriod), 6);
		Future<?> mPeriodicTaskSchedule = null;

		ScheduledThreadPoolTesting(JPanel parent_panel) {
			JPanel border_panel = new JPanel(new BorderLayout());
			parent_panel.add(border_panel);

			JPanel left_border = new JPanel();
			left_border.setLayout(new BoxLayout(left_border, BoxLayout.Y_AXIS));
			border_panel.add(left_border, BorderLayout.WEST);

			final JButton start_thread_btn = new JButton("Start with delay");
			left_border.add(start_thread_btn);
			JPanel tmp_panel = new JPanel();
			tmp_panel.add(mTaskDelayField);
			tmp_panel.add(new JLabel("seconds"));
			left_border.add(tmp_panel);

			final JButton set_task_period_btn = new JButton("Set task period");
			left_border.add(set_task_period_btn);
			tmp_panel = new JPanel();
			tmp_panel.add(mTaskPeriodField);
			tmp_panel.add(new JLabel("seconds"));
			left_border.add(tmp_panel);

			JPanel right_border = new JPanel();
			border_panel.add(right_border, BorderLayout.CENTER);
			mTextArea.setEditable(false);
			border_panel.add(new JScrollPane(mTextArea), BorderLayout.CENTER);

			JPopupMenu clear_menu = new JPopupMenu();
			clear_menu.add(new JMenuItem("Clear")).addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							mTextArea.setText("");
						}
					});
			mTextArea.setComponentPopupMenu(clear_menu);

			// Çàïóñê ïåðèîäè÷åñêîãî âûïîëíåíèÿ çàäà÷è ñ çàäàííûì èíòåðâàëîì
			// âðåìåíè
			// (ò. å. ôèêñèðîâàííûì âðåìåíåì ìåæäó íà÷àëîì âûïîëíåíèÿ çàäà÷è âíå
			// çàâèñèìîñòè îò âðåìåíè âûïîëíåíèÿ ñàìîé çàäà÷è)
			mPeriodicTaskSchedule = mScheduledExecutor.scheduleAtFixedRate(
					new PeriodicTask(), // Çàäà÷à äëÿ âûïîëíåíèÿ
					mTaskPeriod, // Çàäåðæêà ïåðåä ïåðâûì âûïîëíåíèåì çàäà÷è
					mTaskPeriod, // Èíòåðâàë âûïîëíåíèÿ çàäà÷è
					TimeUnit.SECONDS); // Åäèíèöû èçìåðåíèÿ âðåìåíè â
										// ñîîòâåòâóþùèõ ïàðàìåòðàõ ìåòîäà
			// mScheduledExecutor.scheduleWithFixedDelay(...) - ïîâòîðÿþùååñÿ
			// âûïîëíåíèå çàäà÷è ñ çàäàííûì ïåðèîäîì
			// (ò. å. ôèêñèðîâàííûì âðåìåíåì ìåæäó êîíöîì âûïîëíåíèÿ çàäà÷è è
			// íà÷àëîì ñëåäóþùåãî âûïîëíåíèÿ)

			start_thread_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int task_delay = 10;
					try {
						task_delay = Integer.valueOf(mTaskDelayField.getText());
						if (task_delay < 1) {
							task_delay = 1;
						}
					} catch (Exception e) {
					}
					mTaskDelayField.setText(Integer.toString(task_delay));

					// Çàïóñêàåì çàäà÷ó, îòëîæåííóþ íà îïðåäåëåííûé ïðîìåæóòîê
					// âðåìåíè
					mScheduledExecutor.schedule(new DelayedTask(task_delay), // Çàäà÷à
																				// äëÿ
																				// âûïîëíåíèÿ
							task_delay, // Çàäåðæêà ïåðåä ïåðâûì âûïîëíåíèåì
										// çàäà÷è
							TimeUnit.SECONDS); // Åäèíèöû èçìåðåíèÿ âðåìåíè â
												// ñîîòâåòâóþùèõ ïàðàìåòðàõ
												// ìåòîäà
				}
			});

			set_task_period_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					mPeriodicTaskSchedule.cancel(true); // Îòìåíÿåì ïîñëåäóþùåå
														// âûïîëíåíèå
														// ïåðèîäè÷åñêîé çàäà÷è

					try {
						mTaskPeriod = Integer.valueOf(mTaskPeriodField
								.getText());
						if (mTaskPeriod < 1) {
							mTaskPeriod = 1;
						}
					} catch (Exception e) {
						mTaskPeriod = 15;
					}
					mTaskPeriodField.setText(Integer.toString(mTaskPeriod));

					// Çàïóñêàåì ïåðèîäè÷åñêóþ çàäà÷ó ñ íîâûì âðåìåííûì
					// èíòåðâàëîì
					mPeriodicTaskSchedule = mScheduledExecutor
							.scheduleAtFixedRate(new PeriodicTask(),
									mTaskPeriod, mTaskPeriod, TimeUnit.SECONDS);
				}
			});
		}

		private class DelayedTask implements Runnable {
			int mDelay;

			public DelayedTask(int delay) {
				mDelay = delay;
			}

			@Override
			public void run() {
				mTextArea.append("Delayed task: I\'ve started after "
						+ Integer.toString(mDelay) + " seconds delay\n\r");
				try {
					Thread.sleep(mRndGenerator.nextInt(3500) + 500);
					mTextArea.append("Delayed task execution ended...\n\r");
				} catch (InterruptedException e) {
					mTextArea
							.append("Delayed task execution interrupted...\n\r");
				}
				mTextArea.repaint();
			}

		}

		private class PeriodicTask implements Runnable {
			@Override
			public void run() {
				mTextArea.append("Periodic task: I\'m working on interval of "
						+ Integer.toString(mTaskPeriod) + " seconds\n\r");
				try {
					Thread.sleep(mRndGenerator.nextInt(3500) + 500);
					mTextArea.append("Periodic task execution ended...\n\r");
				} catch (InterruptedException e) {
					mTextArea
							.append("Periodic task execution interrupted...\n\r");
				}
				mTextArea.repaint();
			}
		}
	}

	// ------------------------------------------------------------------------------------------------------
	// Êëàññ, äåìîíñòðèðóþùèé ðàáîòó î÷åðåäè çàäà÷ CompletionService, êîòîðûå
	// îæèäàþò ñâîåãî âûïîëíåíèÿ
	private class CompletionServiceTesting {
		Executor mExecutor = Executors.newSingleThreadExecutor(); // Ñîçäàåì
																	// îäíîïîòî÷íûé
																	// ïóë
																	// ïîòîêîâ
		CompletionService<String> mCompletionService // Ñîçäàåì î÷åðåäü íà
														// èñïîëíåíèå çàäà÷
		= new ExecutorCompletionService<String>(mExecutor); // Â êîíñòðóêòîð
															// ïåðåäàåì íàø
															// ýêçåìïëÿð ïóëà
															// ïîòîêîâ mExecutor

		JTextArea mTextArea = new JTextArea();
		JTextField mSubmitMessageField = new JTextField(("Hello message!"), 10);
		JLabel mWaitLabel = new JLabel();

		public CompletionServiceTesting(JPanel parent_panel) {
			JPanel border_panel = new JPanel(new BorderLayout());
			parent_panel.add(border_panel);

			JPanel left_border = new JPanel();
			left_border.setLayout(new BoxLayout(left_border, BoxLayout.Y_AXIS));
			border_panel.add(left_border, BorderLayout.WEST);

			final JButton submit_btn = new JButton("Submit");
			JPanel tmp_panel = new JPanel();
			tmp_panel.add(mSubmitMessageField);
			tmp_panel.add(submit_btn);
			left_border.add(tmp_panel);

			final JButton take_btn = new JButton("Take");
			final JButton poll_btn = new JButton("Poll");
			tmp_panel = new JPanel();
			tmp_panel.add(take_btn);
			tmp_panel.add(poll_btn);
			left_border.add(tmp_panel);

			left_border.add(new JPanel().add(mWaitLabel).getParent());

			JPanel right_border = new JPanel();
			border_panel.add(right_border, BorderLayout.CENTER);
			mTextArea.setEditable(false);
			border_panel.add(new JScrollPane(mTextArea), BorderLayout.CENTER);

			JPopupMenu clear_menu = new JPopupMenu();
			clear_menu.add(new JMenuItem("Clear")).addActionListener(
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							mTextArea.setText("");
						}
					});
			mTextArea.setComponentPopupMenu(clear_menu);

			submit_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// Äåëåãèðóåì íîâóþ çàäà÷ó äëÿ mExecutor ÷åðåç î÷åðåäü
					// mCompletionService
					mCompletionService.submit(new SomeOperation<String>(
							mSubmitMessageField.getText()));
				}
			});

			poll_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// Îïðàøèâàåì íà ïðåäìåò íàëè÷èÿ ðåçóëüòàòà ïî çàïóùåííîé
					// îïåðàöèè
					Future<?> operation_result = mCompletionService.poll(); // -
																			// âûçîâ
																			// ìåòîäà
																			// .poll()
																			// ÍÅ
																			// ÁËÎÊÈÐÓÅÒ
																			// ïîòîê!
					if (operation_result != null) {
						try {
							mTextArea
									.append("Submited operation ended with message: \""
											+ operation_result.get().toString()
											+ "\"...\n\r");
						} catch (Exception e) {
							mTextArea
									.append("Exception occurred in operation_result.get(): "
											+ e.getMessage() + "\n\r");
						}
					} else {
						mTextArea
								.append("No operations has ended at the moment...\n\r");
					}
				}
			});

			new Thread(new Runnable() {
				@Override
				public void run() {
					while (!Thread.interrupted()) {
						try {
							mWaitLabel.setText("IDLE");
							mWaitLabel.repaint();
							synchronized (mWaitLabel) {
								mWaitLabel.wait();
							} // Îæèäàåì ñîîáùåíèÿ íà îáúåêòå mWaitLabel îò
								// âíåøíåãî ïîòîêà
							mWaitLabel.setText("WAITING...");
							mWaitLabel.repaint();

							// Îæèäàåì âûïîëíåíèÿ ïåðâîé îïåðàöèè èç ïóëà
							// ïîòîêîâ è ïîëó÷àåì ðåçóëüòàò åå âûïîëíåíèÿ
							Future<?> operation_result = mCompletionService
									.take(); // - âûçîâ ìåòîäà .take() ÁËÎÊÈÐÓÅÒ
												// ïîòîê!
							if (operation_result != null) {
								mTextArea
										.append("Operation wait has ended, operation returned: \""
												+ operation_result.get()
														.toString()
												+ "\"...\n\r");
							} else {
								mTextArea
										.append("Failed to wait for operation completion...\n\r");
							}
						} catch (Exception e) {
							mTextArea
									.append("Operation wait task exception occurred: "
											+ e.getMessage() + "\n\r");
						}
					}
				}
			}).start();
			take_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// Èñïîëüçóåì ñèíõðîíèçàöèþ íà îáúåêòå mWaitLabel äëÿ
					// óïðàâëåíèÿ ïàðàëëåëüíûì ïîòîêîì (êîòîðûé ìîæåò
					// áëîêèðîâàòüñÿ):
					synchronized (mWaitLabel) {
						mWaitLabel.notifyAll();
					}
				}
			});
		}

		private class SomeOperation<T> implements Callable<T> {
			T mParam;

			public SomeOperation(T param) {
				mParam = param;
			}

			@Override
			public T call() throws Exception {
				mTextArea.append("SomeOperation called with parameter \""
						+ mParam.toString() + "\"\n\r");
				try {
					Thread.sleep(mRndGenerator.nextInt(4500) + 500);
					mTextArea.append("SomeOperation execution ended...\n\r");
				} catch (InterruptedException e) {
					mTextArea
							.append("SomeOperation execution interrupted...\n\r");
				}
				mTextArea.repaint();
				return mParam;
			}

		}
	}
}