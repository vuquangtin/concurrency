package concurrency.java.memory.model.jmm;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class MyRunnable implements Runnable {
	private int a;
	private String b;

	public MyRunnable(int a, String b) {
		this.setA(a);
		this.setB(b);
	}

	@Override
	public void run() {
		int i = 0;
		while (true) {
			a = i;
			b = b + i;
			i++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i > 10)
				break;

		}

	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

}
