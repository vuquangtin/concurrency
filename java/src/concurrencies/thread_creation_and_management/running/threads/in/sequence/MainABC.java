package running.threads.in.sequence;

/***
 * @see https://java2blog.com/print-sequence-3-threads-java/
 * @author admin
 *
 */
public class MainABC {

	public static void main(String[] args) throws InterruptedException {
		MyStateABC state = new MyStateABC();

		final Thread t1 = new Thread(new MyRunnableABC(10, 'A', state));
		final Thread t2 = new Thread(new MyRunnableABC(10, 'B', state));
		final Thread t3 = new Thread(new MyRunnableABC(10, 'C', state));
		t1.start();
		t2.start();
		t3.start();
	}
}

class MyStateABC {
	private char state = 'A';

	public char getState() {
		return state;
	}

	public void incrState() {
		switch (state) {
		case 'A':
			state = 'B';
			return;
		case 'B':
			state = 'C';
			return;
		default:
			state = 'A';
		}
	}
}

class MyRunnableABC implements Runnable {

	private final int max;
	private final char value;
	private final MyStateABC state;

	MyRunnableABC(int max, char value, MyStateABC state) {
		this.max = max;
		this.value = value;
		this.state = state;
	}

	@Override
	public void run() {
		int count = 0;
		while (count < max) {
			synchronized (this.state) {
				if (this.state.getState() == this.value) {
					System.out.print(value);
					count++;
					this.state.incrState();
					//System.out.println(count);
				}
			}
		}
	}
}