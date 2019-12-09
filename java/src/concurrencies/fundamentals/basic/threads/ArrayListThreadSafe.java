package basic.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class ArrayListThreadSafe {

	public static void main(String[] args) {
		ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
				for (int j = 0; j < 10000; j++) {
					arrayList.add(j);
				}
			}).start();
        }
		
		ArrayList<Integer> arrayList1 = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				for (int j = 0; j < 10000; j++) {
					synchronized (arrayList1) {
						arrayList1.add(j);
					}
				}
			}).start();
		}
		
		ArrayList<Integer> arrayList2 = new ArrayList<>();
		List<Integer> syncList = Collections.synchronizedList(arrayList2);
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				for (int j = 0; j < 10000; j++) {
					syncList.add(j);
				}
			}).start();
		}
		
		Vector<Integer> vector = new Vector<>();
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				for (int j = 0; j < 10000; j++) {
					vector.add(j);
				}
			}).start();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(arrayList.size());
		System.out.println(arrayList2.size());
		System.out.println(arrayList1.size());
		System.out.println(vector.size());
	}

}
