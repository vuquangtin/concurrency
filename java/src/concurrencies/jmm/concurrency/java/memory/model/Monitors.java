package concurrency.java.memory.model;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Monitors {
	int a;
	volatile int v;

	void monitors() {
		int i;
		synchronized (this) {
			i = a;
			a = i;
		}
		
		//synchronized (this) {
		//	synchronized (this) {
		//	}
		//}

		i = v;
		synchronized (this) {
		}

		v = i;
		synchronized (this) {
		}
	}
	
	public static void main(final String[] args) {
		Monitors main = new Monitors();
		for(int i = 0; i < 1000000; i++)
			main.monitors();
	}
}