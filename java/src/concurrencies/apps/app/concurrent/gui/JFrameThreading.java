package app.concurrent.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

class MyCallableJFrame2 extends JFrame implements Runnable {

	File file = null;
	Map map = null;
	Set mapKeys = null;
	JTextField tf;

	String frameText = "";

	public MyCallableJFrame2(Object source) throws Exception {
		super();

		if (source instanceof File) {
			file = (File) source;
		} else if (source instanceof Map) {
			map = (Map) source;
			mapKeys = map.keySet();
		} else {
			throw new Exception("Object not File or Map.");
		}

		setLayout(new FlowLayout());
		tf = new JTextField(20);
		add(tf);
		setSize(400, 400);
		setVisible(true);
	}

	void toggle(String text) {
		if (frameText == "") {
			frameText = text;
		} else {
			frameText = "";
		}
	}

	@Override
	public void run() {
		String contentLine = "";

		if (file != null) {

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				contentLine = br.readLine();
				frameText = contentLine;
				tf.setText(frameText);
				System.out.println(frameText);
				Thread.sleep(2000);
				toggle(contentLine);
				tf.setText(frameText);
				System.out.println(frameText);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} else if (map != null) {

			Random rand = new Random();
			int ind = rand.nextInt(4) + 1;
			tf.setText((String) map.get(ind));
			System.out.println(map.get(ind));

		}
	}

}

class MyCallableJFrame extends JFrame implements Callable<String> {

	File file = null;
	Map map = null;
	Set mapKeys = null;
	JTextField tf;

	String frameText = "";

	public MyCallableJFrame(Object source) throws Exception {
		super();

		if (source instanceof File) {
			file = (File) source;
		} else if (source instanceof Map) {
			map = (Map) source;
			mapKeys = map.keySet();
		} else {
			throw new Exception("Object not File or Map.");
		}

		setLayout(new FlowLayout());
		tf = new JTextField(20);
		add(tf);
		setSize(400, 400);
		setVisible(true);
	}

	void toggle(String text) {
		if (frameText == "") {
			frameText = text;
		} else {
			frameText = "";
		}
	}

	@Override
	public String call() throws FileNotFoundException {
		String contentLine = "";

		if (file != null) {

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				contentLine = br.readLine();
				frameText = contentLine;
				tf.setText(frameText);
				Thread.sleep(2000);
				toggle(contentLine);
				tf.setText(frameText);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} else if (map != null) {

			Random rand = new Random();
			int ind = rand.nextInt(4) + 1;
			tf.setText((String) map.get(ind));

		}
		return contentLine;

	}

}

public class JFrameThreading {

	public static void main(String[] args) {

		File file = new File("tester.txt");

		try (FileWriter fr = new FileWriter(file)) {
			if (!file.exists()) {
				file.createNewFile();
			}
			fr.write("Greatest sin is fear.");

		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		String res;

		Map<Integer, String> map = new HashMap<>();

		map.put(1, "Hello from Map");
		map.put(2, "Howdy from Map again");
		map.put(3, "Yoooo!!!");
		map.put(4, "Whats uuuuupppp!!!!");

		ScheduledExecutorService newScheduledThreadPool = Executors
				.newScheduledThreadPool(5);

		try {
			MyCallableJFrame2 ms1 = new MyCallableJFrame2(file);
			MyCallableJFrame2 ms2 = new MyCallableJFrame2(map);

			newScheduledThreadPool.scheduleAtFixedRate(ms1, 0, 1,
					TimeUnit.SECONDS);
			newScheduledThreadPool.scheduleAtFixedRate(ms2, 0, 1,
					TimeUnit.SECONDS);

			// figure out how to call or start the thread.

		} catch (Exception e) {
			e.printStackTrace();
		}

		newScheduledThreadPool.shutdown();

	}

}
