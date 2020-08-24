package app.synchronizers.countdownlatch;

import java.awt.BorderLayout;
import java.util.concurrent.CountDownLatch;

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
public class Fenetre extends JFrame {

	private JButton bouton = new JButton("Décrémenter le compte à rebours");
	private JLabel info = new JLabel();

	private CountDownLatch countDownLatch;

	public Fenetre(CountDownLatch countDownLatch1) {

		countDownLatch = countDownLatch1;

		JPanel panneau = new JPanel();
		panneau.setLayout(new BorderLayout());

		info.setText("Compte à rebours : " + countDownLatch.getCount());
		info.setHorizontalAlignment(JLabel.CENTER);

		panneau.add(bouton, BorderLayout.NORTH);
		panneau.add(info, BorderLayout.SOUTH);
		getContentPane().add(panneau);

		setTitle("CountDownLatch");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();

		bouton.addActionListener((e) -> {
			countDownLatch.countDown();
			info.setText("Compte à rebours : " + countDownLatch.getCount());
		});

		setVisible(true);
	}
}