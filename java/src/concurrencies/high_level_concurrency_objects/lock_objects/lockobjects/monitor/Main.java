package lockobjects.monitor;

/**
 * Design Patterns
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/designpattern">https://github.com/vuquangtin/designpattern</a>
 *
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		final BoundedBuffer bb = new BoundedBuffer(3);
		System.out.println("Trying to deposit");
		bb.deposit("h");
		System.out.println("Trying to deposit");
		bb.deposit("e");
		System.out.println("Trying to deposit");
		bb.deposit("l");
		System.out.println("Trying to deposit");
		bb.deposit("l");
		System.out.println("Nothing to deposit anymore");
		new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("Trying to fetch");
					bb.fetch();
					System.out.println("fetched");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
