package app.concurrent.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import javax.swing.border.CompoundBorder;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class MainFrame extends JFrame implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private static final String version = "v0.2 [BETA]";
	private JPanel contentPane;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel bottomPanel;
	private JPanel bottomLeftPanel;
	private JPanel bottomRightPanel;

	private JButton ummmButton;
	private JButton understandButton;
	private JButton okButton;
	private JButton noButton;
	private JButton huhButton;
	private JButton staresButton;
	private JButton questionsButton;
	private JButton clearButton;
	private JButton keyButton;
	private JButton reportButton;
	private JButton copyButton;
	private JButton saveButton;
	private JButton settingsButton;

	private JTextArea textArea;

	private int ummmCounter = 0;
	private int understandCounter = 0;
	private int okCounter = 0;
	private int noCounter = 0;
	private int huhCounter = 0;
	private int staresCounter = 0;
	private int questionsCounter = 0;
	private int clearCounter = 0;
	private int keyCounter = 0;

	private boolean isSaved = true;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/logo.png"));
		setBackground(Color.WHITE);
		setTitle("Staines Counter " + version);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(750, 500);
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent windowEvent) {
				if (isSaved == false) {
					int choice = JOptionPane.showConfirmDialog(null,
							"Do you want to save?", "Save",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (choice == JOptionPane.YES_OPTION) {
						saveAction();
					} else if (choice == JOptionPane.NO_OPTION) {
						System.exit(0);
					} else {

					}
				} else {
					System.exit(0);
				}
			}
		});

		contentPane = new JPanel();
		contentPane.setForeground(new Color(128, 0, 0));
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

		setContentPane(contentPane);

		init();
	}

	public void init() {
		// Buttons
		ummmButton = new JButton("Ummm");
		ummmButton.setForeground(Color.WHITE);
		ummmButton.setBackground(new Color(128, 0, 0));
		ummmButton.addActionListener(this);

		understandButton = new JButton("Understand?");
		understandButton.setForeground(Color.WHITE);
		understandButton.setBackground(new Color(128, 0, 0));
		understandButton.addActionListener(this);

		okButton = new JButton("Ok");
		okButton.setForeground(Color.WHITE);
		okButton.setBackground(new Color(128, 0, 0));
		okButton.addActionListener(this);

		noButton = new JButton("No?");
		noButton.setForeground(Color.WHITE);
		noButton.setBackground(new Color(128, 0, 0));
		noButton.addActionListener(this);

		huhButton = new JButton("Huh");
		huhButton.setForeground(Color.WHITE);
		huhButton.setBackground(new Color(128, 0, 0));
		huhButton.addActionListener(this);

		staresButton = new JButton("*stares*");
		staresButton.setForeground(Color.WHITE);
		staresButton.setBackground(new Color(128, 0, 0));
		staresButton.addActionListener(this);

		questionsButton = new JButton("Questions?");
		questionsButton.setForeground(Color.WHITE);
		questionsButton.setBackground(new Color(128, 0, 0));
		questionsButton.addActionListener(this);

		clearButton = new JButton("It is clear?");
		clearButton.setForeground(Color.WHITE);
		clearButton.setBackground(new Color(128, 0, 0));
		clearButton.addActionListener(this);

		keyButton = new JButton("Key Point");
		keyButton.setForeground(Color.WHITE);
		keyButton.setBackground(new Color(128, 0, 0));
		keyButton.addActionListener(this);

		copyButton = new JButton("Copy Text");
		copyButton.setForeground(Color.WHITE);
		copyButton.setBackground(new Color(128, 0, 0));
		copyButton.addActionListener(this);

		reportButton = new JButton("Get Report");
		reportButton.setForeground(Color.WHITE);
		reportButton.setBackground(new Color(128, 0, 0));
		reportButton.addActionListener(this);

		settingsButton = new JButton("");
		settingsButton.setForeground(Color.WHITE);
		settingsButton.setBackground(Color.WHITE);
		settingsButton.setIcon(new ImageIcon("img/settings.png"));
		settingsButton.setBounds(80, 0, 35, 35);
		settingsButton.setToolTipText("Settings");
		settingsButton.addActionListener(this);
		settingsButton.setBorderPainted(false);

		saveButton = new JButton("Save");
		saveButton.setBackground(new Color(128, 0, 0));
		saveButton.setForeground(Color.WHITE);
		saveButton.addActionListener(this);

		// Text Area
		textArea = new JTextArea();
		textArea.setFont(new Font("Dialog", Font.PLAIN, 14));
		textArea.setRows(20);
		textArea.setWrapStyleWord(true);
		textArea.setForeground(new Color(128, 0, 0));
		textArea.setEditable(false);
		textArea.setLineWrap(true);

		// Panels
		topPanel = new JPanel();
		topPanel.setForeground(new Color(128, 0, 0));
		topPanel.setBackground(Color.WHITE);
		topPanel.setLayout(new GridLayout(0, 3, 10, 10));
		topPanel.add(ummmButton);
		topPanel.add(understandButton);
		topPanel.add(okButton);
		topPanel.add(noButton);
		topPanel.add(huhButton);
		topPanel.add(staresButton);
		topPanel.add(questionsButton);
		topPanel.add(clearButton);
		topPanel.add(keyButton);

		middlePanel = new JPanel();
		middlePanel.setBorder(new CompoundBorder(
				new EmptyBorder(10, 10, 10, 10), new LineBorder(new Color(0, 0,
						0))));
		middlePanel.setForeground(new Color(128, 0, 0));
		middlePanel.setBackground(Color.WHITE);
		middlePanel.setLayout(new CardLayout(10, 10));
		middlePanel.add(textArea, "name_127379771693100");

		bottomLeftPanel = new JPanel();
		bottomLeftPanel.setBackground(Color.WHITE);
		FlowLayout fl_bottomLeftPanel = (FlowLayout) bottomLeftPanel
				.getLayout();
		fl_bottomLeftPanel.setHgap(25);
		bottomLeftPanel.add(copyButton);
		bottomLeftPanel.add(reportButton);
		bottomLeftPanel.add(saveButton);

		bottomRightPanel = new JPanel();
		bottomRightPanel.setBackground(Color.WHITE);
		bottomRightPanel.setLayout(null);
		bottomRightPanel.add(settingsButton);

		bottomPanel = new JPanel();
		bottomPanel.setForeground(new Color(128, 0, 0));
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.add(bottomLeftPanel);
		bottomPanel.add(bottomRightPanel);

		contentPane.add(topPanel, BorderLayout.NORTH);
		contentPane.add(middlePanel, BorderLayout.CENTER);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ummmButton) {
			ummmAction();
		} else if (e.getSource() == understandButton) {
			understandAction();
		} else if (e.getSource() == okButton) {
			okAction();
		} else if (e.getSource() == noButton) {
			noAction();
		} else if (e.getSource() == huhButton) {
			huhAction();
		} else if (e.getSource() == staresButton) {
			staresAction();
		} else if (e.getSource() == questionsButton) {
			questionsAction();
		} else if (e.getSource() == clearButton) {
			clearAction();
		} else if (e.getSource() == keyButton) {
			keyAction();
		} else if (e.getSource() == reportButton) {
			reportAction();
		} else if (e.getSource() == copyButton) {
			copyAction();
		} else if (e.getSource() == saveButton) {
			saveAction();
		} else if (e.getSource() == settingsButton) {
			settingsAction();
		}
		this.requestFocus();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
			questionsAction();
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
			clearAction();
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
			keyAction();
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
			noAction();
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
			huhAction();
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
			staresAction();
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
			ummmAction();
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
			understandAction();
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
			okAction();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void ummmAction() {
		textArea.setText(textArea.getText() + "ummm...");
		ummmCounter++;
		isSaved = false;
	}

	public void understandAction() {
		textArea.setText(textArea.getText() + "understand?...");
		understandCounter++;
		isSaved = false;
	}

	public void okAction() {
		textArea.setText(textArea.getText() + "ok...");
		okCounter++;
		isSaved = false;
	}

	public void noAction() {
		textArea.setText(textArea.getText() + "no...");
		noCounter++;
		isSaved = false;
	}

	public void huhAction() {
		textArea.setText(textArea.getText() + "huh...");
		huhCounter++;
		isSaved = false;
	}

	public void staresAction() {
		textArea.setText(textArea.getText() + "*stares*...");
		staresCounter++;
		isSaved = false;
	}

	public void questionsAction() {
		textArea.setText(textArea.getText() + "Any questions?...");
		questionsCounter++;
		isSaved = false;
	}

	public void clearAction() {
		textArea.setText(textArea.getText() + "Is it clear?...");
		clearCounter++;
		isSaved = false;
	}

	public void keyAction() {
		textArea.setText(textArea.getText()
				+ "Did you understand this KEY POINT...");
		keyCounter++;
		isSaved = false;
	}

	public void reportAction() {
		JOptionPane.showMessageDialog(null, "ummm(s): " + ummmCounter
				+ "\nUnderstand?(s): " + understandCounter + "\nOk(s): "
				+ okCounter + "\nNo?(s): " + noCounter + "\nHuh(s): "
				+ huhCounter + "\n*stares*: " + staresCounter
				+ "\nQuestions?: " + questionsCounter + "\nIt is clear?(s): "
				+ clearCounter + "\nKey point(s): " + keyCounter,
				"Staines Counter Report", JOptionPane.PLAIN_MESSAGE);
	}

	public void copyAction() {
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(new StringSelection(textArea.getText()), null);
	}

	public void saveAction() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			Date date = new Date();

			BufferedWriter bw = new BufferedWriter(new FileWriter("logs/"
					+ dateFormat.format(date) + ".txt"));

			bw.write(dateFormat.format(date));
			bw.newLine();
			bw.write(String.valueOf("Ummm(s): " + ummmCounter));
			bw.newLine();
			bw.write(String.valueOf("Understand?(s): " + understandCounter));
			bw.newLine();
			bw.write(String.valueOf("Ok(s): " + okCounter));
			bw.newLine();
			bw.write(String.valueOf("No?(s): " + noCounter));
			bw.newLine();
			bw.write(String.valueOf("Huh(s): " + huhCounter));
			bw.newLine();
			bw.write(String.valueOf("*stares*:" + staresCounter));
			bw.newLine();
			bw.write(String.valueOf("Questions?:" + questionsCounter));
			bw.newLine();
			bw.write(String.valueOf("It is clear?(s): " + clearCounter));
			bw.newLine();
			bw.write(String.valueOf("Key point(s): " + keyCounter));
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		isSaved = true;
	}

	public void settingsAction() {
		JOptionPane.showMessageDialog(null,
				"This feature is not yet available!", "Unavailable Feature",
				JOptionPane.PLAIN_MESSAGE);
	}
}
