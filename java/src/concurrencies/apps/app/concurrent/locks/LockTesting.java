package app.concurrent.locks;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class LockTesting {

	Random mRandom = new Random(System.currentTimeMillis());

	public LockTesting() {
		JFrame myFrame = new JFrame("Lock Testing");
		myFrame.setSize(700, 800);
		myFrame.setLocationRelativeTo(null);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel main_panel = new JPanel(new BorderLayout());
		JPanel center_panel = new JPanel(new GridLayout(0, 1));
		main_panel.add(center_panel, BorderLayout.CENTER);

		new ReentrantLockTesting(center_panel);
		new ReadWriteLockTesting(center_panel);
		new ConditionLockTesting(center_panel);

		myFrame.add(main_panel);
		myFrame.setVisible(true);
	}

	public static void main(String[] args) {
		new LockTesting();
	}

	// **********************************************************************************************
	// Êëàññ, äåìîíñòðèðóþùèé ðàáîòó êëàññà ïðîñòîé áëîêèðîâêè ReentrantLock
	private class ReentrantLockTesting {
		// Ñîçäàåì îáúåêò êëàññà ReentrantLock - ïðîñòàÿ áëîêèðîâêà íà ïîòîêå ñ
		// âîçìîæíîñòüþ ïîâòîðíîãî âçÿòèÿ áëîêèðîâêè âíóòðè ïîòîêà,
		// óæå âëàäåþùåãî äàííîé áëîêèðîâêîé
		Lock mReentrantLock = new ReentrantLock(true /*
													 * Äàííûé ïàðàìåòð óêàçûâàåò
													 * íà "÷åñòíîñòü"
													 * ðàñïðåäåëåíèÿ áëîêèðîâêè
													 */);
		// ×åñòíîé ÿâëÿåòñÿ áëîêèðîâêà, êîòîðàÿ ïðè âûáîðå ñâîåãî ñëåäóþùåãî
		// âëàäåëüöà ó÷èòûâàåò õðîíîëîãèþ çàïðîñîâ íà ïîëó÷åíèå áëîêèðîâêè
		// Ïðèìå÷àòåëüíî òî, ÷òî âñòðîåííûå áëîêèðîâêè Java (íà îïåðàòîðå
		// synchronized) ÷åñòíûìè ÍÅ ßÂËßÞÒÑß

		static final int THREAD_SLEEP_TIME_MS = 6500;

		public ReentrantLockTesting(JPanel parent_panel) {
			JPanel grid_panel = new JPanel(new GridLayout(0, 2));
			grid_panel.setBorder(BorderFactory
					.createTitledBorder("ReentrantLock(true) testing"));
			parent_panel.add(grid_panel);

			new LockPicker(grid_panel);
			new LockPicker(grid_panel);
			new LockPicker(grid_panel);
			new LockTryTester(grid_panel);
		}

		private class LockPicker implements Runnable {
			JButton mLockBtn = new JButton("get Lock");
			JLabel mLockLabel = new JLabel("...");

			LockPicker(JPanel grid_panel) {
				grid_panel.add(new JPanel().add(mLockBtn).getParent());
				grid_panel.add(new JPanel().add(mLockLabel).getParent());

				mLockBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						synchronized (LockPicker.this) {
							LockPicker.this.notify(); // Ñîîáùàåì ïîòîêó, ÷òîáû
														// îí íà÷àë äåéñòâîâàòü
						}
					}
				});

				new Thread(this).start();
			}

			public void run() {
				while (!Thread.interrupted()) {
					try {
						synchronized (this) {
							this.wait(); // Æäåì êîìàíäû èçâíå, ÷òîáû íà÷àòü
											// äåéñòâîâàòü
						}
						mLockLabel.setText("Trying to get Lock...");
						mLockLabel.repaint();
						mReentrantLock.lock(); // Ïîëó÷àåì áëîêèðîâêó
												// mReentrantLock

						mLockLabel.setText("I am the Lock owner!");
						mLockLabel.repaint();
						Thread.sleep(mRandom.nextInt(THREAD_SLEEP_TIME_MS) + 1000);
					} catch (Exception e) {
						mLockLabel.setText(e.getMessage());
						mLockLabel.repaint();
					} finally {
						mReentrantLock.unlock(); // Îñâîáîæäàåì áëîêèðîâêó
													// mReentrantLock
						// Â îòëè÷èè îò âñòðîåííûõ áëîêèðîâîê Java, API
						// áëîêèðîâîê Lock ìîãóò
						// ñîçäàâàòü ìåðòâûå áëîêèðîâêè, åñëè íå ïðèíèìàòü
						// ñîîòâåòñòâóþùèõ ìåð ïðåäîñòîðîæíîñòè!
						mLockLabel.setText("...");
						mLockLabel.repaint();
					}
				}
			}
		}

		private class LockTryTester implements Runnable {
			JButton mTryLockBtn = new JButton("try Lock");
			JLabel mTryLockLabel = new JLabel("...");

			LockTryTester(JPanel grid_panel) {
				grid_panel.add(new JPanel().add(mTryLockBtn).getParent());
				grid_panel.add(new JPanel().add(mTryLockLabel).getParent());

				mTryLockBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						synchronized (LockTryTester.this) {
							LockTryTester.this.notify(); // Ñîîáùàåì ïîòîêó,
															// ÷òîáû îí íà÷àë
															// äåéñòâîâàòü
						}
					}
				});

				new Thread(this).start();
			}

			public void run() {
				while (!Thread.interrupted()) {
					boolean lock_aqcuired = false;
					try {
						synchronized (this) {
							this.wait(); // Æäåì êîìàíäû èçâíå, ÷òîáû íà÷àòü
											// äåéñòâîâàòü
						}
						mTryLockLabel
								.setText("Trying to get Lock during 2 seconds...");
						mTryLockLabel.repaint();
						lock_aqcuired = mReentrantLock.tryLock(2,
								TimeUnit.SECONDS); // Ïðîáóåì ïîëó÷èòü
													// áëîêèðîâêó mReentrantLock
													// â òå÷åíèè 2-õ ñåêóíä

						if (lock_aqcuired) {
							// Áëîêèðîâêà áûëà óñïåøíî çàõâà÷åíà çà îòâåäåííîå
							// âðåìÿ
							mTryLockLabel.setText("I am the Lock owner!");
							mTryLockLabel.repaint();
							Thread.sleep(mRandom.nextInt(THREAD_SLEEP_TIME_MS) + 1000);
						} else {
							// Áëîêèðîâêó çàõâàòèòü íå óäàëîñü çà îòâåäåííîå
							// âðåìÿ...
							mTryLockLabel
									.setText("Failed to get the Lock in given time...");
							mTryLockLabel.repaint();
							Thread.sleep(3000);
						}

					} catch (Exception e) {
						mTryLockLabel.setText(e.getMessage());
						mTryLockLabel.repaint();
					} finally {
						if (lock_aqcuired) {
							mReentrantLock.unlock(); // Îñâîáîæäàåì áëîêèðîâêó
														// mReentrantLock, åñëè
														// îíà áûëà óñïåøíî
														// çàõâà÷åíà ïîòîêîì
						}
						// Â îòëè÷èè îò âñòðîåííûõ áëîêèðîâîê Java, API
						// áëîêèðîâîê Lock ìîãóò
						// ñîçäàâàòü ìåðòâûå áëîêèðîâêè, åñëè íå ïðèíèìàòü
						// ñîîòâåòñòâóþùèõ ìåð ïðåäîñòîðîæíîñòè!
						mTryLockLabel.setText("...");
						mTryLockLabel.repaint();
					}
				}
			}
		}
	}

	// **********************************************************************************************
	// Êëàññ, äåìîíñòðèðóþùèé ðàáîòó êëàññà áëîêèðîâêè ÷òåíèÿ/çàïèñè
	private class ReadWriteLockTesting {
		// Ñîçäàåì îáúåêò êëàññà ReentrantReadWriteLock - áëîêèðîâêà,
		// ðàçäåëÿþùàÿ ñâîèõ ïîëüçîâàòåëåé íà ÷èòàòåëåé è ïèñàòåëåé:
		// ÷èòàòåëü ìîæåò ïîëó÷èòü áëîêèðîâêó óæå çàõâà÷åííóþ äðóãèì ÷èòàòåëåì
		// (íî íå ïèñàòåëåì);
		// ïèñàòåëü ìîæåò ïîëó÷èòü áëîêèðîâêó òîëüêî êîãäà îíà îñâîáîäèòñÿ îò
		// âñåõ äðóãèõ ïèñàòåëåé è ÷èòàòåëåé
		ReadWriteLock mReentrantReadWriteLock = new ReentrantReadWriteLock(
				false /* Ñîçäàåì íå îáúåêòèâíóþ áëîêèðîâêó */);

		static final int THREAD_SLEEP_TIME_MS = 6500;

		public ReadWriteLockTesting(JPanel parent_panel) {
			JPanel grid_panel = new JPanel(new GridLayout(0, 2));
			grid_panel
					.setBorder(BorderFactory
							.createTitledBorder("ReentrantReadWriteLock(false) testing"));
			parent_panel.add(grid_panel);

			new Reader(grid_panel);
			new Reader(grid_panel);
			new Writer(grid_panel);
			new Writer(grid_panel);

		}

		private class Reader implements Runnable {
			JButton mReadBtn = new JButton("readLock()");
			JLabel mLabel = new JLabel("...");

			public Reader(JPanel grid_panel) {
				grid_panel.add(new JPanel().add(mReadBtn).getParent());
				grid_panel.add(new JPanel().add(mLabel).getParent());

				mReadBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						synchronized (Reader.this) {
							Reader.this.notify(); // Ñîîáùàåì ïîòîêó, ÷òîáû îí
													// íà÷àë äåéñòâîâàòü
						}
					}
				});

				new Thread(this).start();
			}

			public void run() {
				while (!Thread.interrupted()) {
					try {
						synchronized (this) {
							this.wait(); // Æäåì êîìàíäû èçâíå, ÷òîáû íà÷àòü
											// äåéñòâîâàòü
						}
						mLabel.setText("Trying to get Lock for reading...");
						mLabel.repaint();
						mReentrantReadWriteLock.readLock().lock(); // Ïðîáóåì
																	// ïîëó÷èòü
																	// áëîêèðîâêó
																	// mReentrantReadWriteLock
																	// íà ÷òåíèå

						mLabel.setText("I am the ReadLock owner!");
						mLabel.repaint();
						Thread.sleep(mRandom.nextInt(THREAD_SLEEP_TIME_MS) + 1000);
					} catch (Exception e) {
						mLabel.setText(e.getMessage());
						mLabel.repaint();
					} finally {
						mReentrantReadWriteLock.readLock().unlock(); // Îñâîáîæäàåì
																		// áëîêèðîâêó
																		// mReentrantReadWriteLock
																		// íà
																		// ÷òåíèå
						mLabel.setText("...");
						mLabel.repaint();
					}
				}
			}
		}

		private class Writer implements Runnable {
			JButton mWriteBtn = new JButton("writeLock()");
			JLabel mLabel = new JLabel("...");

			public Writer(JPanel grid_panel) {
				grid_panel.add(new JPanel().add(mWriteBtn).getParent());
				grid_panel.add(new JPanel().add(mLabel).getParent());

				mWriteBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						synchronized (Writer.this) {
							Writer.this.notify(); // Ñîîáùàåì ïîòîêó, ÷òîáû îí
													// íà÷àë äåéñòâîâàòü
						}
					}
				});

				new Thread(this).start();
			}

			public void run() {
				while (!Thread.interrupted()) {
					try {
						synchronized (this) {
							this.wait(); // Æäåì êîìàíäû èçâíå, ÷òîáû íà÷àòü
											// äåéñòâîâàòü
						}
						mLabel.setText("Trying to get Lock for writing...");
						mLabel.repaint();
						mReentrantReadWriteLock.writeLock().lock(); // Ïðîáóåì
																	// ïîëó÷èòü
																	// áëîêèðîâêó
																	// mReentrantReadWriteLock
																	// íà çàïèñü

						mLabel.setText("I am the WriteLock owner!");
						mLabel.repaint();
						Thread.sleep(mRandom.nextInt(THREAD_SLEEP_TIME_MS) + 1000);
					} catch (Exception e) {
						mLabel.setText(e.getMessage());
						mLabel.repaint();
					} finally {
						mReentrantReadWriteLock.writeLock().unlock(); // Îñâîáîæäàåì
																		// áëîêèðîâêó
																		// mReentrantReadWriteLock
																		// íà
																		// çàïèñü
						mLabel.setText("...");
						mLabel.repaint();
					}
				}
			}
		}
	}

	// **********************************************************************************************
	// Êëàññ, äåìîíñòðèðóþùèé ðàáîòó ñ ñèãíàëàìè â êîíòåêñòå ïàêåòà
	// java.util.concurrent.locks
	private class ConditionLockTesting {
		Lock mLock = new ReentrantLock(); // Ñîçäàåì ïðîñòóþ áëîêèðîâêó òèïà
											// ReentrantLock

		// Ñîçäàåì óñëîâèå íà çàäàííîé áëîêèðîâêå (ïðè ïîìîùè óñëîâèé âîçìîæåí
		// îáìåí ñèãíàëàìè ìåæäó ïîòîêàìè)
		// Êàæäàÿ áëîêèðîâêà ìîæåò èìåòü íåñêîëüêî óñëîâèé
		Condition mCondition = mLock.newCondition();

		public ConditionLockTesting(JPanel parent_panel) {
			JPanel border_panel = new JPanel(new BorderLayout());
			border_panel.setBorder(BorderFactory
					.createTitledBorder("Condition testing"));
			parent_panel.add(border_panel);

			JPanel grid_panel = new JPanel(new GridLayout(0, 1));
			border_panel.add(grid_panel, BorderLayout.CENTER);

			JPanel left_panel = new JPanel();
			left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.Y_AXIS));
			border_panel.add(left_panel, BorderLayout.WEST);

			JButton tmp_btn;

			(tmp_btn = new JButton("signal()"))
					.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								mLock.lock(); // Ðàáîòà ñ ñèãíàëàìè âîçìîæíà
												// òîëüêî ïðè ïîëó÷åíèè
												// ñîîòâåòñòâóþùåé áëîêèðîâêè
												// (òàêæå êàê è ñî âñòðîåííûìè
												// áëîêèðîâêàìè Java -
												// îñîáåííîñòü ðåàëèçàöèè API)
								// Ïîäàåì îäèíî÷íûé ñèãíàë ïîòîêàì (ñèãíàë
								// ïðèìåò âñåãî îäèí ïîòîê, îòîçâàâøèéñÿ íà íåãî
								// ïåðâûì):
								mCondition.signal();
							} finally {
								mLock.unlock();
							}
						}
					});
			left_panel.add(new JPanel().add(tmp_btn).getParent());

			(tmp_btn = new JButton("signalAll()"))
					.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								mLock.lock();
								// Ïîäàåì øèðîêîâåùàòåëüíûé ñèãíàë ïîòîêàì
								// (ñèãíàë ïðèìóò âñå ïîòîêè, îæèäàþùèå åãî â
								// äàííûé ìîìåíò):
								mCondition.signalAll();
							} finally {
								mLock.unlock();
							}
						}
					});
			left_panel.add(new JPanel().add(tmp_btn).getParent());

			new SignalReceiver(grid_panel, "Thread #1");
			new SignalReceiver(grid_panel, "Thread #2");
			new SignalReceiver(grid_panel, "Thread #3");
			new SignalReceiver(grid_panel, "Thread #4");
		}

		private class SignalReceiver implements Runnable {
			JLabel mSignalLabel = new JLabel();

			public SignalReceiver(JPanel grid_panel, String title) {
				JPanel tmp_panel = new JPanel();
				tmp_panel.setBorder(BorderFactory.createTitledBorder(title));
				tmp_panel.add(mSignalLabel);
				grid_panel.add(tmp_panel);
				new Thread(this).start();
			}

			@Override
			public void run() {
				while (!Thread.interrupted()) {
					mSignalLabel.setText("Waiting for signal...");
					mSignalLabel.repaint();
					try {
						try {
							mLock.lock(); // Ðàáîòà ñ ñèãíàëàìè âîçìîæíà òîëüêî
											// ïðè ïîëó÷åíèè ñîîòâåòñòâóþùåé
											// áëîêèðîâêè
											// (òàêæå êàê è ñî âñòðîåííûìè
											// áëîêèðîâêàìè Java - îñîáåííîñòü
											// ðåàëèçàöèè API)
							mCondition.await(); // Æäåì ñîáûòèÿ èçâíå
						} finally {
							mLock.unlock();
						}
						mSignalLabel.setText("Signal received!");
						mSignalLabel.repaint();
						Thread.sleep(3500);
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}

}