package collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class ProcessSomething {
	private List<Integer> integerList = Collections.synchronizedList(new ArrayList<>());

	private void calculate() {
		for (int i = 0; i < 10000; i++) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ex) {
				Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
			}
			integerList.add(new Random().nextInt(100));
		}
	}

	private void calculate2() {
		for (int i = 0; i < 10000; i++) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ex) {
				Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
			}
			integerList.add(new Random().nextInt(100));
		}
	}

	public void process() {
		Long start = System.currentTimeMillis();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				calculate();
			}
		});
		t1.start();
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				calculate2();
			}
		});
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException ex) {
			Logger.getLogger(ProcessSomething.class.getName()).log(Level.SEVERE, null, ex);
		}
		Long end = System.currentTimeMillis();
		System.out.println("Duration: " + (end - start));
		System.out.println("List size: " + integerList.size());
	}
}

public class App {
	public static void main(String[] args) {
		new ProcessSomething().process();
	}
}