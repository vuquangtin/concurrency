package app.synchronizers.apps;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TraditionalSynchronizationTesting {
	
	Random mRandom = new Random( System.currentTimeMillis() );
	
	public TraditionalSynchronizationTesting () {
		JFrame myFrame = new JFrame ( "Traditional Synchronization Testing" );
		myFrame.setSize(700, 850);
		myFrame.setLocationRelativeTo(null);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel main_panel = new JPanel (new BorderLayout());
		JPanel center_panel = new JPanel(new GridLayout(0, 1));
		main_panel.add(center_panel, BorderLayout.CENTER);
		
		new CountDownLatchTesting 	(center_panel);
		new SemaphoreTesting 		(center_panel);
		new CyclicBarrierTesting 	(center_panel);
		new ExchangerTesting 		(center_panel);
		
		myFrame.add(main_panel);
		myFrame.setVisible(true);
	}

	public static void main(String[] args) {
		new TraditionalSynchronizationTesting();
	}
	
	// **********************************************************************************************
	// Êëàññ, äåìîíñòðèðóþùèé ðàáîòó êëàññà ñèíõðîíèçàöèè CountDownLatch
	private class CountDownLatchTesting {
		List<Runnable> mWorkersList = new Vector<>();
		List<Runnable> mWaitingList = new Vector<>();
		ExecutorService mCashedPool = Executors.newCachedThreadPool();
		
		// Äàííàÿ óòèëèòà ñèíõðîíèçàöèè ïîçâîëÿåò ïîòîêàì îæèäàòü çíà÷åíèÿ CountDownLatch ïîêà îíî íå ñòàíåò ðàâíûì 0
		// (äàííîå çíà÷åíèå óñòàíàâëèâàåòñÿ ïðè èíèöèàëèçàöèè è ìîæåò èçìåíÿòüñÿ òîëüêî â ìåíüøóþ ñòîðîíó çà âðåìÿ æèçíè îáåêòà) 
		CountDownLatch mCountDownLatch = null;
		
		public CountDownLatchTesting ( JPanel parent_panel ) {
			JPanel border_panel = new JPanel ( new BorderLayout() );
			border_panel.setBorder(BorderFactory.createTitledBorder("Count Down Latch Testing"));
			parent_panel.add(border_panel);
			
			JPanel box_panel = new JPanel ();
			box_panel.setLayout(new BoxLayout(box_panel, BoxLayout.Y_AXIS));
			border_panel.add(box_panel, BorderLayout.CENTER);
			
			JPanel left_panel = new JPanel ();
			left_panel.setLayout( new BoxLayout(left_panel, BoxLayout.Y_AXIS) );
			border_panel.add(left_panel, BorderLayout.WEST);
			
			JButton start_btn = new JButton("Start threads");
			left_panel.add(new JPanel().add(start_btn).getParent());
			
			JButton stop_btn = new JButton("Stop Threads Now!");
			stop_btn.setEnabled(false);
			left_panel.add(new JPanel().add(stop_btn).getParent());
			
			mWorkersList.add( new WorkingThread (box_panel, "First Working Thread") );
			mWorkersList.add( new WorkingThread (box_panel, "Second Working Thread") );
			
			mWaitingList.add( new WaitingThread (box_panel, "First Waiting Thread") );
			mWaitingList.add( new WaitingThread (box_panel, "Second Waiting Thread") );
			
			start_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					start_btn.setEnabled(false);
					stop_btn.setEnabled(true);
					
					mCountDownLatch = new CountDownLatch (mWorkersList.size());
							
					for (Runnable runnable : mWorkersList) {
						mCashedPool.execute( runnable );
					}
					
					for (Runnable runnable : mWaitingList) {
						mCashedPool.execute( runnable );
					}
					
					new Thread ( new Runnable() {
						@Override
						public void run() {
							try {
								// Îæèäàåì çàâåðøåíèÿ ðàáîòàþùèõ ïîòîêîâ äëÿ âîçâðàòà êîíòðîëüíûõ ýëåìåíòîâ â èñõîäíîå ñîñòîÿíèå:
								mCountDownLatch.await();
							} catch (InterruptedException e) {
							} finally {
								start_btn.setEnabled(true);
								stop_btn.setEnabled(false);
							}
						}
					} ).start();
				}
			});
			
			stop_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// Çàâåðøàåì âñå ïîòîêè èç ïóëà:
					mCashedPool.shutdownNow();
					try {
						mCashedPool.awaitTermination(1000, TimeUnit.MILLISECONDS);
					} catch (InterruptedException e) {
					} finally {	// Ñîçäàåì íîâûé ïóë äëÿ âîçìîæíîãî ïîâòîðíîãî çàïóñêà ïîòîêîâ:
						mCashedPool = Executors.newCachedThreadPool();
					}
				}
			});
		}
		
		private class WorkingThread implements Runnable {
			JLabel mLabel = new JLabel("IDLE");
			
			public WorkingThread ( JPanel box_panel, String thread_name ) {
				JPanel tmp_panel = new JPanel();
				tmp_panel.setBorder ( BorderFactory.createTitledBorder(thread_name) );
				tmp_panel.add(mLabel);
				box_panel.add ( tmp_panel );
			}
			
			protected void setProgress ( double progress ) {
				if ( progress > 100.0 ) { progress = 100.0; } 
				
				mLabel.setText( "Working progress: " + Integer.toString((int)progress) );
				mLabel.repaint();
			}

			@Override
			public void run() {
				double run_progress = 0.0;
				int run_speed = mRandom.nextInt(350) + 50;
				setProgress(run_progress);
				
				try {
					while ( run_progress < 100.0 ) {
						Thread.sleep(40);
						run_progress += (double)run_speed / 100.0;
						setProgress(run_progress);
					}
					mLabel.setText("Work complete!");
				} catch ( InterruptedException e ) {
					mLabel.setText("INTERRUPTED");
				} finally {
					mCountDownLatch.countDown(); // Óìåíüøàåì çíà÷åíèå ñ÷åò÷èêà íà 1
				}
			}
		}
		
		private class WaitingThread implements Runnable {
			JLabel mLabel = new JLabel("IDLE");
			
			public WaitingThread ( JPanel box_panel, String thread_name ) {
				JPanel tmp_panel = new JPanel();
				tmp_panel.setBorder ( BorderFactory.createTitledBorder(thread_name) );
				tmp_panel.add(mLabel);
				box_panel.add ( tmp_panel );
			}
			
			@Override
			public void run() {
				try {
					mLabel.setText("Waiting for Working threads...");
					
					mCountDownLatch.await(); // Îæèäàåì ïîêà mCountDownLatch íå ñòàíåò ðàâíî 0 (ñ÷åò÷èê óìåíüøàåòñÿ â ïîòîêàõ WorkingThread)
					
					mLabel.setText("All Work done!");
				} catch ( InterruptedException e ) {
					mLabel.setText("INTERRUPTED");
				}
			}
		}
	}
	
	// **********************************************************************************************
	// Êëàññ, äåìîíñòðèðóþùèé ðàáîòó êëàññà ñåìàôîðîâ â Java: Semaphore
	private class SemaphoreTesting {
		// Êëàññ ñåìàôîðà â Java ðàáîòàåò ïîäîáíî áëîêèðîâêå Lock ñ 2-ìÿ îòëè÷èÿìè:
		// 1. Ñåìàôîðû â Java ÿâëÿþòñÿ ñ÷åòíûìè (ò. å. ïðåäñòàâëÿþò ñîáîé ïðîèçâîëüíîå öåëîå ÷èñëî)
		// 2. Ó ñåìàôîðà íåò ôàêòè÷åñêîãî âëàäåëüöà (ò. å. íåò ðàçíèöû êàêîé ïîòîê çàâëàäåë ñåìàôîðîì, à êàêîé áóäåò åãî îòïóñêàòü)
		Semaphore mSemaphore = new Semaphore(0/* Íà÷àëüíîå çíà÷åíèå ñ÷åò÷èêà */, true /* "×åñòíîñòü" ïðè áëîêèðîâêå ïîòîêîâ ñåìàôîðîì */);
		
		int mSemaphoreCounter = 0;
		JLabel mSemaphoreCounterLabel = new JLabel("Semaphore = 0"); 
		
		private void IncSemaphoreCounter () {
			mSemaphoreCounterLabel.setText("Semaphore = " + Integer.toString(++mSemaphoreCounter));
		}
		
		private void DecSemaphoreCounter () {
			mSemaphoreCounterLabel.setText("Semaphore = " + Integer.toString(--mSemaphoreCounter));
		}
		
		public SemaphoreTesting ( JPanel parent_panel ) {
			JPanel border_panel = new JPanel ( new BorderLayout() );
			border_panel.setBorder(BorderFactory.createTitledBorder("Semaphore Testing"));
			parent_panel.add(border_panel);
			
			JPanel box_panel = new JPanel ();
			box_panel.setLayout(new BoxLayout(box_panel, BoxLayout.Y_AXIS));
			border_panel.add(box_panel, BorderLayout.CENTER);
			
			JPanel left_panel = new JPanel ();
			left_panel.setLayout( new BoxLayout(left_panel, BoxLayout.Y_AXIS) );
			border_panel.add(left_panel, BorderLayout.WEST);
			
			JButton release_btn = new JButton("Release Semaphore");
			left_panel.add( new JPanel ().add(release_btn).getParent() );
			left_panel.add( new JPanel ().add(mSemaphoreCounterLabel).getParent() );
			release_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mSemaphore.release(); // Îòïóñêàåì ñåìàôîð â ïîòîêå îáðàáîòêè êîíòðîëüíûõ ýëåìåíòîâ GUI
					IncSemaphoreCounter ();
				}
			});
			
			new Thread ( new WaitingThread (box_panel, "Waiting Thread #1") ).start();
			new Thread ( new WaitingThread (box_panel, "Waiting Thread #2") ).start();
			new Thread ( new WaitingThread (box_panel, "Waiting Thread #3") ).start();
		}
		
		private class WaitingThread implements Runnable {
			JLabel mLabel = new JLabel();
			
			public WaitingThread ( JPanel box_panel, String thread_name ) {
				JPanel tmp_panel = new JPanel();
				tmp_panel.setBorder ( BorderFactory.createTitledBorder(thread_name) );
				tmp_panel.add(mLabel);
				box_panel.add ( tmp_panel );
			}
			
			@Override
			public void run() {
				while (!Thread.interrupted())
				{
					try {
						mLabel.setText("Waiting for Semaphore...");
						
						Thread.sleep( mRandom.nextInt(2000) + 500 );
						
						mSemaphore.acquire(); // Îæèäàåì ïîêà äðóãîé ïîòîê íå âûçîâåò release()
						DecSemaphoreCounter ();
						
						mLabel.setText("Semaphore Acquired!");
						Thread.sleep( mRandom.nextInt(3000) + 2000 );
					} catch ( InterruptedException e ) {
						mLabel.setText("INTERRUPTED");
					}
				}
			}
		}
	}
	
	// **********************************************************************************************
	// Êëàññ, äåìîíñòðèðóþùèé ñîçäàíèå òî÷åê öèêëè÷åñêîé ñèíõðîíèçàöèè ïðè ïîìîùè êëàññà CyclicBarrier
	private class CyclicBarrierTesting {
		JTextArea mLeftArea = new JTextArea("https://translate.google.ru\nhttps://docs.oracle.com\n");
		JTextArea mRightArea = new JTextArea(0, 30);
		
		{
			mRightArea.setFont(new Font (Font.MONOSPACED, 0, 11));
		}
		
		ExecutorService mPool = null;
	
		public CyclicBarrierTesting ( JPanel parent_panel ) {
			JPanel border_panel = new JPanel( new BorderLayout() );
			border_panel.setBorder(BorderFactory.createTitledBorder("Cyclic Barrier Testing"));
			parent_panel.add(border_panel);
			
			JPanel horizont_box_panel = new JPanel ();
			horizont_box_panel.setLayout(new BoxLayout(horizont_box_panel, BoxLayout.X_AXIS));
			border_panel.add(horizont_box_panel, BorderLayout.CENTER);
			
			JPanel left_panel = new JPanel (new BorderLayout());
			horizont_box_panel.add(left_panel);
			left_panel.add(mLeftArea, BorderLayout.CENTER);
			horizont_box_panel.add(Box.createHorizontalStrut(5));
			JPanel right_panel = new JPanel(new BorderLayout());
			right_panel.add(mRightArea, BorderLayout.CENTER);
			horizont_box_panel.add(right_panel);
			
			JButton update_btn = new JButton("Update URLs");
			border_panel.add(new JPanel().add(update_btn).getParent(), BorderLayout.SOUTH);
			
			startExample ();
			
			update_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					startExample ();
				}
			});
		}
		
		private class Result implements Comparable<Result> {
			Long time;
			String site;
			Result( Long time, String site ) { 
				this.time = new Long (time); 
				this.site = new String (site);
			}
			public int compareTo( Result r ) {
				if ( r != null ) 
					return site.compareTo( r.site );
				return -1; 
			}
			
			public String toString () {
				if ( time >= 0 ) {
					return String.format( "%-30.30s : %d", site, time );
				} else {
					return String.format( "%-30.30s : Invalid URL", site );
				}
			}
		}
		
		private long timeConnect ( String site )
		{
			long start = System.currentTimeMillis();
			try { 
				new URL( site ).openConnection().connect(); 
			} catch ( IOException e ) { 
				return -1;
			}
			return System.currentTimeMillis() - start;
		}
		
		private void printList ( List<?> list ) {
			String str = "";
			for ( Object elem : list ) {
				str += elem.toString() + "\n";
			}
			mRightArea.setText(str + "------------------");
			mRightArea.repaint();
		}
		
		public void startExample ()
		{
			if ( mPool != null ) {
				mPool.shutdownNow();
				mPool = null;
			}
			mPool = Executors.newCachedThreadPool();
		
			List<Result> results = new ArrayList<>();
			List<String> urlList = new ArrayList<>();
			String [] args = mLeftArea.getText().split("[\n\r]"); // Ðàçáèâàåì ñòðîêó ïî ñèìâîëàì ïåðåâîäà ñòðîêè \n èëè \r
			for ( String arg : args ) {
				if ( arg.length() > 1 ) {	// Äîáàâëÿåì â ìàññèâ URL òîëüêî íå ïóñòûå ïóòè:
					urlList.add(arg);
				}
			} 
			
			Runnable showResultsAction = new Runnable() { 
				public void run() { 
					Collections.sort( results );
					printList (results);
					results.clear();
				} 
			};
			
			// Êëàññ CyclicBarrier ïîçâîëÿåò îæèäàòü ïîêà çàäàííîå êîëè÷åñòâî ïîòîêîâ íå äîéäóò äî åäèíîé òî÷êè âûïîëíåíèÿ
			CyclicBarrier barrier = new CyclicBarrier( 
					urlList.size(),		// Êîëè÷åñòâî ïîòîêîâ, òðåáóþùèõ ñèíõðîíèçàöèè  
					showResultsAction );// Îïöèîíàëüíî çàäàåòñÿ äåéñòâèå, êîòîðîå áóäåò çàïóùåíî ïðè äîñòèæåíèè òî÷êè ñèíõðîíèçàöèè
			// !! Ïðèìå÷àíèå: áîëåå ãèáêèì êëàññîì, ïîõîæèì ïî íàçíà÷åíèþ íà CyclicBarrier, ÿâëÿåòñÿ êëàññ Phaser,
			// ïîçâîëÿþùèé îòñëåæèâàòü êîëè÷åñòâî èòåðàöèé â òî÷êå ñèíõðîíèçàöèè (íîìåð ôàçû), à òàêæå
			// äèíàìè÷åñêè èçìåíÿòü êîëè÷åñòâî ïîòîêîâ ïðèõîäÿùèõ ê áàðüåðó
					
			for ( final String site : urlList ) {
				mPool.execute( new Runnable () {
					public void run() {
						while( true ) {
							long time = timeConnect( site );
							results.add( new Result( time, site ) );
							try {
								barrier.await(); // Çàäàåì òî÷êó ñèíõðîíèçàöèè äëÿ äàííîãî öèêëè÷åñêîãî áàðüåðà
								// Êàê òîëüêî çàäàííîå êîëè÷åñòâî ïîòîêîâ äîñòèãíåò äàííîãî áàðüåðà, ïîòîê ïðîäîëæèò âûïîëíåíèå,
								// à áàðüåð âåðíåòñÿ â èñõîäíîå ñîñòîÿíèå è áóäåò ãîòîâ äëÿ ïîâòîðíîãî èñïîëüçîâàíèÿ
							} catch ( BrokenBarrierException e ) { 
								return; 
							} catch ( InterruptedException e ) { 
								return; 
							}
						}
					}
				});
			}
		}
	}
	
	// **********************************************************************************************
	// Êëàññ, äåìîíñòðèðóþùèé èñïîëüçîâàíèå êëàññà ñèíõðîíèçàöèè Exchanger
	private class ExchangerTesting {
		JTextArea mLeftArea 	= new JTextArea("Frist Buffer");
		JTextArea mRightArea 	= new JTextArea("Second Buffer");
		
		// Êëàññ Exchanger ïîçâîëÿåò ñèíõðîíèçèðîâàòü ïàðó ïîòîêîâ äëÿ îáìåíà äàííûìè
		// Äàííûé êëàññ îòëè÷àåòñÿ îò SynchronousQueue òåì, ÷òî ïåðåäàåò äàííûå â äâóõ íàïðàâëåíèÿõ
		Exchanger<StringBuffer> mExchanger = new Exchanger<>();
		
		public ExchangerTesting ( JPanel parent_panel ) {
			JPanel border_panel = new JPanel( new BorderLayout() );
			border_panel.setBorder(BorderFactory.createTitledBorder("Exchanger Testing"));
			parent_panel.add(border_panel);
			
			JPanel horizont_box_panel = new JPanel ();
			horizont_box_panel.setLayout(new BoxLayout(horizont_box_panel, BoxLayout.X_AXIS));
			border_panel.add(horizont_box_panel, BorderLayout.CENTER);
			
			JPanel left_panel = new JPanel (new BorderLayout());
			horizont_box_panel.add(left_panel);
			left_panel.add(mLeftArea, BorderLayout.CENTER);
			horizont_box_panel.add(Box.createHorizontalStrut(5));
			JPanel right_panel = new JPanel(new BorderLayout());
			right_panel.add(mRightArea, BorderLayout.CENTER);
			horizont_box_panel.add(right_panel);
			
			JButton exchange_btn = new JButton("Exhange Buffers");
			border_panel.add(new JPanel().add(exchange_btn).getParent(), BorderLayout.SOUTH);
			
			new Thread() {	// Ïîòîê ¹1
				public void run() {
					StringBuffer buffer1 = new StringBuffer(mLeftArea.getText());
					
					while (!Thread.interrupted()) {
						try {
							synchronized (exchange_btn) { exchange_btn.wait(); }	// Æäåì íàæàòèÿ ïîëüçîâàòåëüñêîé êíîïêè
							
							// Çàïîëíÿåì áóôåð äàííûìè
							buffer1.setLength(0);
							buffer1.append(mLeftArea.getText());
							
							// Îáìåíèâàåìñÿ áóôåðîì ñ äðóãèì ïîòîêîì
							buffer1 = mExchanger.exchange(buffer1);
							
							mLeftArea.setText(buffer1.toString()); mLeftArea.repaint();
						} catch ( InterruptedException e ) {}
					}	
				}
			}.start();
			
			new Thread() {	// Ïîòîê ¹2
				public void run() {
					StringBuffer buffer2 = new StringBuffer(mRightArea.getText());
					
					while (!Thread.interrupted()) {
						try {
							// Äàííûé ïîòîê áëîêèðóåòñÿ ïîêà êàêîé ëèáî äðóãîé ïîòîê íå çàõî÷åò îáìåíÿòüñÿ äàííûìè íà îáúåêòå mExchanger
							buffer2 = mExchanger.exchange(buffer2);
							
							mRightArea.setText(buffer2.toString()); mRightArea.repaint();
						} catch ( InterruptedException e ) {}
					}	
				}
			}.start();
			
			exchange_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					synchronized (exchange_btn) { exchange_btn.notifyAll(); }
				}
			});
		}
	}

}