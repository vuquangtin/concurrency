package synchronizers.models;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class DataBuffer {
	private List<String> data = new ArrayList<String>();
	private int bound;

	public DataBuffer(int bound) {
		super();
		this.bound = bound;
	}

	public void add(String entry) {
		if (!isFull()) {
			data.add(entry);
		}
	}

	public String take() {
		if (!isEmpty()) {
			String a = data.get(0);
			data.remove(0);
			return a;
		}
		return null;
	}

	public String get() {
		if (!isEmpty()) {
			return data.get(0);
		}
		return null;

	}

	public boolean isFull() {
		return data.size() < bound;
	}

	public boolean isEmpty() {
		return data.size() == 0;
	}
}
