package app.concurrent.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class OrtographyPanel extends JPanel implements Runnable {

	private JMenuBar mBar;
	private JMenu styleMenu, fileMenu;
	private JMenuItem odczyt, zapis, dyktando, kursywa, pogrubienie;
	private JEditorPane textPane;
	private JButton checkButton;

	private String text, textFromPane;
	private Font textFont;
	private File file;

	public OrtographyPanel() {
		this.setPreferredSize(new Dimension(600, 400));

		// menu
		mBar = new JMenuBar();
		styleMenu = new JMenu("Styl");
		fileMenu = new JMenu("Plik");
		odczyt = new JMenuItem("Odczytaj");
		odczyt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(
						"/Users/juleswinnfield/Documents/Programowanie/Java/Labkowe/Programy/lab8/files");
				int returnVal = fileChooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
				}

				FileReader fr;
				String readFromFile = "";
				// OTWIERANIE PLIKU:
				try {
					fr = new FileReader(file);
					BufferedReader bfr = new BufferedReader(fr);

					// ODCZYT KOLEJNYCH LINII Z PLIKU:
					while ((readFromFile = bfr.readLine()) != null) {
						text = readFromFile;
						// System.out.println(text);
						textPane.setText(text);
					}

					// ZAMYKANIE PLIKU
					fr.close();
				} catch (Exception e1) {
					System.out.println("BLAD IO!");
					// System.exit(1);
				}

			}
		});

		dyktando = new JMenuItem("Start dyktanda");
		dyktando.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String dyktanded = dyktandation(textPane.getText());
				textPane.setText(dyktanded);
			}
		});

		zapis = new JMenuItem("Zapisz");
		zapis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					textFromPane = textPane.getText();
					File file = new File("out/outputFile.txt");
					if (!file.exists())
						file.createNewFile();
					FileWriter out = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(out);
					bw.write(textFromPane);
					bw.close();
					System.out.println("file saved");

				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});

		kursywa = new JMenuItem("Kursywa");
		kursywa.setFont(new Font(kursywa.getFont().getFontName(), Font.ITALIC,
				kursywa.getFont().getSize()));
		kursywa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textPane.setFont(new Font(textPane.getFont().getFontName(),
						Font.ITALIC, textPane.getFont().getSize()));
			}
		});

		pogrubienie = new JMenuItem("Pogrubienie");
		pogrubienie.setFont(new Font(pogrubienie.getFont().getFontName(),
				Font.BOLD, pogrubienie.getFont().getSize()));
		pogrubienie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textPane.setFont(new Font(textPane.getFont().getFontName(),
						Font.BOLD, textPane.getFont().getSize()));
			}
		});

		styleMenu.add(kursywa);
		styleMenu.add(pogrubienie);
		fileMenu.add(odczyt);
		fileMenu.add(dyktando);
		fileMenu.add(zapis);
		mBar.add(fileMenu);
		mBar.add(styleMenu);

		// textPane
		textPane = new JEditorPane();

		// button

		checkButton = new JButton("Sprawdź");
		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckingOrtography checker = new CheckingOrtography();
				String mistakes = null;
				textFromPane = textPane.getText();
				if (text != null && !text.isEmpty()) {
					checker.setTexts(text, textFromPane);
					try {
						checker.checkDyktando();
					} catch (OrtographyException o) {
						mistakes = Arrays.toString(o.getMistake().toArray());
						if (mistakes.length() > 2) {
							System.out.println(mistakes);
							JOptionPane.showMessageDialog(null,
									"W dyktandzie są błędy!", "Słabiutko",
									JOptionPane.WARNING_MESSAGE);
						}
					}
					if (mistakes.length() == 2)
						JOptionPane.showMessageDialog(null,
								"Dyktando napisane poprawnie.\n"
										+ "Wygrałeś życie", "BRAWO!",
								JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		this.setLayout(new BorderLayout());
		this.add(BorderLayout.NORTH, mBar);
		this.add(BorderLayout.CENTER, textPane);
		this.add(BorderLayout.SOUTH, checkButton);
		this.setVisible(true);
	}

	public String dyktandation(String s) {// zażółć gęślą jaźń
		String tmp = s;
		tmp = tmp.replace("rz", "??");
		tmp = tmp.replace("ch", "??");
		tmp = tmp.replace('ą', '?');
		tmp = tmp.replace('ę', '?');
		tmp = tmp.replace('ó', '?');
		tmp = tmp.replace('u', '?');
		tmp = tmp.replace('h', '?');
		tmp = tmp.replace('ń', '?');
		tmp = tmp.replace('ś', '?');
		tmp = tmp.replace('ż', '?');
		tmp = tmp.replace('ź', '?');
		tmp = tmp.replace('ł', '?');
		return tmp;
	}

	@Override
	public void run() {
	}
}