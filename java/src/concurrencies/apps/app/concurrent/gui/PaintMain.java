package app.concurrent.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;
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

public class PaintMain extends JFrame {
	JMenuBar menuBar;
	JPanel toolPanel, drawPanel;
	JButton pencilButton, rubberButton, boxButton, lineButton, circleButton;
	int option = 1;
	int lineWidth;
	Color lineColor = Color.black;
	JLabel image;

	public PaintMain() throws HeadlessException {
		super("PaintMain");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// this.setLocationRelativeTo(null);
		this.setSize(600, 500);

		this.setJMenuBar(menuBar = new JMenuBar());
		this.add(toolPanel = new JPanel(), BorderLayout.WEST);

		// ----Menu-----
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem menuItem1 = new JMenuItem("Save");
		menuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveImage(drawPanel);
			}
		});
		JMenuItem menuItem2 = new JMenuItem("Import file");
		menuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int result = chooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String sName = file.getAbsolutePath();
					image = new JLabel("", new ImageIcon(sName), JLabel.CENTER);
					drawPanel.add(image, BorderLayout.CENTER);
					drawPanel.revalidate();
					drawPanel.repaint();
				}
			}
		});
		JMenuItem menuItem3 = new JMenuItem("New");
		menuItem3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PaintMain.super.remove(drawPanel);
				drawPanel = new DraftingBoard(lineWidth);
				PaintMain.this.add(drawPanel, BorderLayout.CENTER);
				PaintMain.this.setVisible(true);
			}
		});
		fileMenu.add(menuItem1);
		fileMenu.add(menuItem2);
		fileMenu.add(menuItem3);

		JMenu colorMenu = new JMenu("Line options");
		JMenuItem menuItem4 = new JMenuItem("Set line color");
		menuItem4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lineColor = JColorChooser.showDialog(null, "Line color",
						lineColor);
				((DraftingBoard) drawPanel).setColor(lineColor);

			}
		});
		JMenu submenu = new JMenu("Set line width");
		JMenuItem menuItem5 = new JMenuItem("1 px");
		menuItem5.setActionCommand("1");
		menuItem5.addActionListener(new lineWidthListener());
		JMenuItem menuItem6 = new JMenuItem("5 px");
		menuItem6.setActionCommand("5");
		menuItem6.addActionListener(new lineWidthListener());
		JMenuItem menuItem7 = new JMenuItem("10 px");
		menuItem7.setActionCommand("10");
		menuItem7.addActionListener(new lineWidthListener());
		submenu.add(menuItem5);
		submenu.add(menuItem6);
		submenu.add(menuItem7);
		colorMenu.add(menuItem4);
		colorMenu.add(submenu);
		menuBar.add(colorMenu);

		// ----ToolPanel----
		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.PAGE_AXIS));
		Border grayLine = BorderFactory.createLineBorder(new Color(51, 51, 51));
		toolPanel.setBorder(grayLine);
		TitledBorder title = BorderFactory.createTitledBorder(grayLine,
				"Toolkit");
		toolPanel.setBorder(title);
		pencilButton = new JButton("pencil");
		pencilButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				option = 1;
				((DraftingBoard) drawPanel).setNumber(option);
			}

		});
		rubberButton = new JButton("rubber");
		rubberButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				option = 2;
				((DraftingBoard) drawPanel).setNumber(option);
			}
		});
		boxButton = new JButton("square");
		boxButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				option = 3;
				((DraftingBoard) drawPanel).setNumber(option);
			}
		});
		lineButton = new JButton("line");
		lineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				option = 4;
				((DraftingBoard) drawPanel).setNumber(option);
			}
		});
		circleButton = new JButton("circle");
		circleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				option = 5;
				((DraftingBoard) drawPanel).setNumber(option);
			}
		});

		toolPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		toolPanel.add(pencilButton);
		toolPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		toolPanel.add(rubberButton);
		toolPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		toolPanel.add(boxButton);
		toolPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		toolPanel.add(lineButton);
		toolPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		toolPanel.add(circleButton);

		// ----DrawPanel------

		drawPanel = new DraftingBoard(lineWidth);
		this.add(drawPanel, BorderLayout.CENTER);
		((DraftingBoard) drawPanel).setNumber(option);
		((DraftingBoard) drawPanel).setColor(lineColor);

	}

	private void saveImage(JPanel panel) {
		BufferedImage img = new BufferedImage(panel.getWidth(),
				panel.getHeight(), BufferedImage.TYPE_INT_RGB);
		panel.paint(img.getGraphics());
		try {
			ImageIO.write(img, "png", new File("paint.png"));
			System.out.println("panel saved as image");

		} catch (Exception e) {
			System.out.println("panel not saved" + e.getMessage());
		}
	}

	public class lineWidthListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			lineWidth = Integer.parseInt(e.getActionCommand());
			((DraftingBoard) drawPanel).setWidth(lineWidth);
		}
	}

	public static void main(String[] args) {
		PaintMain paint = new PaintMain();
		paint.setVisible(true);

	}

}